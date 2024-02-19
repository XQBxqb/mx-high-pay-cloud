package cn.high.mx.module.mission.service.impl;


import cn.high.mx.framework.redis.config.RedissonService;
import cn.high.mx.module.mission.api.dto.GoodsDTO;
import cn.high.mx.module.mission.cache.redis.consts.RedisConst;
import cn.high.mx.module.mission.dao.GoodsMapper;
import cn.high.mx.module.mission.dao.SeckillGoodsMapper;
import cn.high.mx.module.mission.dataobj.Goods;
import cn.high.mx.module.mission.dataobj.SeckillGoods;
import cn.high.mx.module.mission.dto.GoodsDetailDTO;
import cn.high.mx.module.mission.res.RestRes;
import cn.high.mx.module.mission.service.BaseService;
import cn.high.mx.module.mission.service.GoodService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodServiceImpl extends BaseService implements GoodService {
    private final GoodsMapper goodsMapper;

    private final SeckillGoodsMapper seckillGoodsMapper;

    private final RedissonService redissonService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Object> addGood(GoodsDTO goodsDTO) {
        Goods goods = copyGoods(goodsDTO);;
        goodsMapper.insert(goods);
        SeckillGoods seckillGoods = new SeckillGoods(goods.getId(), goods.getGoodsStock());
        seckillGoodsMapper.insert(seckillGoods);
        return RestRes.ok();
    }

    @Override
    public RestRes<PageInfo<GoodsDTO>> getGoodPage(Integer page, Integer pageSize, String goodsName) {
        List<Goods> goodsList;
        if (StringUtils.isBlank(goodsName)) {
            PageHelper.startPage(page, pageSize);
            goodsList = goodsMapper.selectAll();
        } else {
            PageHelper.startPage(page, pageSize);
            goodsList = goodsMapper.findByNameLike(goodsName);
        }
        List<GoodsDTO> goodsDTOS = getGoodsDTOS(goodsList);
        return RestRes.ok(new PageInfo<>(goodsDTOS));
    }

    private List<GoodsDTO> getGoodsDTOS(List<Goods> goodsList) {
        List<GoodsDTO> goodsDTOS = goodsList.stream()
                                            .map(this::copyGoodsDTO)
                                            .collect(Collectors.toList());
        return goodsDTOS;
    }

    private GoodsDTO copyGoodsDTO(Goods goods) {
        return GoodsDTO.builder()
                       .goodsImg(goods.getGoodsImg())
                       .goodsPrice(goods.getGoodsPrice())
                       .goodsStock(goods.getGoodsStock())
                       .goodsTitle(goods.getGoodsTitle())
                       .goodsName(goods.getGoodsName())
                       .id(goods.getId())
                       .isUsing(goods.getIsUsing())
                       .endTime(goods.getEndTime())
                       .startTime(goods.getStartTime())
                       .build();
    }
    private Goods copyGoods(GoodsDTO goods) {
        return Goods.builder()
                       .goodsImg(goods.getGoodsImg())
                       .goodsPrice(goods.getGoodsPrice())
                       .goodsStock(goods.getGoodsStock())
                       .goodsTitle(goods.getGoodsTitle())
                       .goodsName(goods.getGoodsName())
                       .id(goods.getId())
                       .isUsing(goods.getIsUsing())
                       .endTime(goods.getEndTime())
                       .startTime(goods.getStartTime())
                       .build();
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public RestRes<Object> updateOne(GoodsDTO goodsDTO) {
        Goods goods = new Goods();
        copy(goods,goodsDTO);
        goodsMapper.updateById(goods);
        seckillGoodsMapper.updateGoods(goodsDTO);
        redissonService.delete(RedisConst.PREFIX_GOOD_NUMS+":"+goodsDTO.getId());
        redissonService.delete(RedisConst.PREFIX_GOOD_END_TIME+":"+goodsDTO.getId());
        return RestRes.ok();
    }
    @Override
    public RestRes<GoodsDTO> goodsEdit(Long id) {
        Goods goods = goodsMapper.selectById(id);
        GoodsDTO goodsDTO = copyGoodsDTO(goods);
        return RestRes.ok(goodsDTO);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public RestRes<Object> goodsUsing(Long id) {
        goodsMapper.updateUsing(id);
        return RestRes.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RestRes<Object> goodsDelete(Long id) {
        goodsMapper.deleteById(id);
        seckillGoodsMapper.deleteById(id);
        redissonService.delete(RedisConst.PREFIX_GOOD_NUMS+":"+id);
        redissonService.delete(RedisConst.PREFIX_GOOD_END_TIME+":"+id);
        return RestRes.ok();
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public RestRes<Object> goodsDeletes(String ids) {
        String[] split = ids.split(",");
        List<Long> goodsIds = Arrays.stream(split)
                                    .map(Long::valueOf).collect(Collectors.toList());
        goodsIds.forEach(this::goodsDelete);
        return RestRes.ok();
    }

    @Override
    public RestRes<List<GoodsDetailDTO>> getGoods() {
        List<Goods> goodsList = goodsMapper.selectAll();
        long currented = System.currentTimeMillis();
        List<GoodsDetailDTO> list = new ArrayList<>();
        goodsList.forEach(t->{
            if(!t.getIsUsing())
                return;
            addDto(currented, list, t);
        });
        return RestRes.ok(list);
    }

    @Override
    public RestRes<GoodsDetailDTO> getDetail(long goodId) {
        long currented = System.currentTimeMillis();
        Goods goods = goodsMapper.selectById(goodId);
        return RestRes.ok(getDetailVo(currented,goods));
    }

    private static void addDto(long currented, List<GoodsDetailDTO> list, Goods t) {
        GoodsDetailDTO goodsDetailVo = getDetailVo(currented, t);
        list.add(goodsDetailVo);
    }

    private static GoodsDetailDTO getDetailVo(long currented, Goods t) {
        GoodsDetailDTO goodsDetailVo = new GoodsDetailDTO();
        long startTime = Timestamp.valueOf(t.getStartTime())
                             .getTime();
        long endTime = Timestamp.valueOf(t.getEndTime())
                             .getTime();
        if(currented < startTime){
            goodsDetailVo.setRemainSeconds((int)((startTime - currented) / 1000));
        }else if(currented > endTime){
            goodsDetailVo.setRemainSeconds(-1);
        }else goodsDetailVo.setRemainSeconds(0);
        goodsDetailVo.setGoods(t);
        goodsDetailVo.setStockCount(t.getGoodsStock());
        return goodsDetailVo;
    }
}
