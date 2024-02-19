package cn.high.mx.module.mission.service.impl;

import cn.high.mx.framework.redis.config.RedissonService;
import cn.high.mx.module.mission.api.dto.SeckillGoodsDTO;
import cn.high.mx.module.mission.dao.GoodsMapper;
import cn.high.mx.module.mission.dao.OrderInfoMapper;
import cn.high.mx.module.mission.dao.SeckillGoodsMapper;
import cn.high.mx.module.mission.dataobj.Goods;
import cn.high.mx.module.mission.dataobj.OrderInfo;
import cn.high.mx.module.mission.dataobj.SeckillGoods;
import cn.high.mx.module.mission.exception.enums.BizStatusEnum;
import cn.high.mx.module.mission.cache.redis.consts.RedisConst;
import cn.high.mx.module.mission.cache.redis.LUAService;
import cn.high.mx.module.mission.mq.dto.GoodsMsgDto;
import cn.high.mx.module.mission.mq.publisher.CommonPublisher;
import cn.high.mx.module.mission.res.RestRes;
import cn.high.mx.module.mission.service.BaseService;
import cn.high.mx.module.mission.service.SeckillService;
import cn.high.mx.module.mission.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeckillServiceImpl extends BaseService implements SeckillService {
    private final RedissonService redissonService;

    private final OrderInfoMapper orderInfoMapper;
    
    private final LUAService luaService;
    
    private final CommonPublisher commonPublisher;
    
    
    private final GoodsMapper goodsMapper;

    private final SeckillGoodsMapper seckillGoodsMapper;
    @Override
    public RestRes<String> getPath(long goodId, HttpServletRequest request) {
        Long uid = parseToken(request);
        if(uid == null){
            return RestRes.errorEnum(BizStatusEnum.RES_SUCCESS);
        }
        return RestRes.ok(generatorPath(uid,goodId));
    }

    @Override
    public RestRes<Integer> doSeckill(long goodId, String path, HttpServletRequest request) {
        long uid = parseToken(request);
        Long orderId = generatorOrderId(uid, goodId);
        syncCache(goodId);
        RestRes<Integer> errorSeckillReq = errorRes(goodId, path, uid);
        if (errorSeckillReq != null) return errorSeckillReq;
        //这里LUA需要判断商品日期，预减数量，能够完成商品订购就更新订单状态为1,日志就以订单状态为基准
        //LUA脚本
        int res = luaService.orderJudge(goodId,path);
        if(res == -1){
            return RestRes.errorEnum(BizStatusEnum.RES_ORDER_FAILED);
        }
        //发送消息
        commonPublisher.publishMsg(GoodsMsgDto.builder().goodId(goodId).userId(uid).orderId(orderId).build());
        return RestRes.ok(0);
    }
    //避免键值过期但商品仍有效导致的订单失效
    private void syncCache(long goodId) {
        Integer num = redissonService.get(RedisConst.PREFIX_GOOD_NUMS + ":" + goodId, Integer.class);
        Long endTime = null;
        if(num == null){
            Goods goods = goodsMapper.selectById(goodId);
            num = goods.getGoodsStock();
            endTime = DateUtil.LocalDateTimeToTimeStamp(goods.getEndTime());
            redissonService.set(RedisConst.PREFIX_GOOD_NUMS + ":" + goodId,num,RedisConst.DEFAULT_EXPIRE_TIME);
            redissonService.set(RedisConst.PREFIX_GOOD_END_TIME+":"+goodId,endTime,RedisConst.DEFAULT_EXPIRE_TIME);
        }
    }

    private RestRes<Integer> errorRes(long goodId, String path, long uid) {
        if(!generatorPath(uid, goodId).equals(path)){
            return RestRes.errorEnum(BizStatusEnum.RES_PATH_AUTHEN_ERROR);
        }
        Integer status = redissonService.get(RedisConst.PREFIX_ORDER_STATUS + ":" + path, Integer.class);
        if(status != null){
            return RestRes.errorEnum(BizStatusEnum.RES_ORDER_EXIST);
        }
        return null;
    }

    @Override
    public RestRes<Long> doResult(long goodId, HttpServletRequest request) {
        long uid = parseToken(request);
        String path = generatorPath(uid, goodId);
        Integer status = redissonService.get(RedisConst.PREFIX_ORDER_STATUS + ":" + path, Integer.class);
        if (status == null) {
            //要么就是没有订单，要么就是有订单要么就是redis订单状态过期

            //判断是不是redis过期原因
            Long orderId = generatorOrderId(uid, goodId);
            OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
            if(orderInfo == null){
                return RestRes.ok(-1L);
            }
            redissonService.set(RedisConst.PREFIX_ORDER_STATUS + ":" + path,3,RedisConst.DEFAULT_EXPIRE_TIME);
            return RestRes.ok(orderId);
        }
        if(status == 1 || status == 2 || status == 4)
            return RestRes.ok(0L);
        return RestRes.ok(generatorOrderId(uid, goodId));
    }

    @Override
    public RestRes<PageInfo<SeckillGoodsDTO>> getPage(Integer page, Integer pageSize, Long goodsId) {
        List<SeckillGoods> seckillGoods;
        if (goodsId == null) {
            PageHelper.startPage(page, pageSize);
            seckillGoods = seckillGoodsMapper.selectAll();
        } else {
            PageHelper.startPage(page, pageSize);
            seckillGoods = Collections.singletonList(seckillGoodsMapper.selectById(goodsId));
        }
        List<SeckillGoodsDTO> dtoList = copyDto(seckillGoods);
        return RestRes.ok(new PageInfo<>(dtoList));
    }
    private static List<SeckillGoodsDTO> copyDto(List<SeckillGoods> seckillGoods) {
        List<SeckillGoodsDTO> dtoList = seckillGoods.stream()
                                                    .map(t -> {
                                                        SeckillGoodsDTO seckillGoodsDTO = new SeckillGoodsDTO();
                                                        seckillGoodsDTO.setGoodsId(t.getGoodsId());
                                                        seckillGoodsDTO.setStockCount(t.getStockCount());
                                                        return seckillGoodsDTO;
                                                    })
                                                    .collect(Collectors.toList());
        return dtoList;
    }
}
