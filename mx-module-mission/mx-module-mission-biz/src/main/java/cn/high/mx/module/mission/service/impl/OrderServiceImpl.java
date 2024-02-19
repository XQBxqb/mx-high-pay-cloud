package cn.high.mx.module.mission.service.impl;


import cn.high.mx.module.mission.api.dto.OrderDTO;
import cn.high.mx.module.mission.cache.redis.consts.RedisConst;
import cn.high.mx.module.mission.dao.GoodsMapper;
import cn.high.mx.module.mission.dao.OrderInfoMapper;
import cn.high.mx.module.mission.dataobj.Goods;
import cn.high.mx.module.mission.dataobj.OrderInfo;
import cn.high.mx.module.mission.dto.OrderDetailDTO;
import cn.high.mx.module.mission.res.RestRes;
import cn.high.mx.module.mission.service.BaseService;
import cn.high.mx.module.mission.service.OrderService;
import cn.high.mx.module.mission.utils.JwtConsts;
import cn.high.mx.module.mission.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends BaseService implements OrderService {
    private final OrderInfoMapper orderInfoMapper;

    private final GoodsMapper goodsMapper;
    @Override
    public RestRes<PageInfo<OrderDTO>> findOrders(Integer page, Integer pageSize, Long id) {

        List<OrderInfo> orderInfos;
        if(id == null){
            PageHelper.startPage(page,pageSize);
            orderInfos = orderInfoMapper.selectAll();
        }else{
            PageHelper.startPage(page,pageSize);
            orderInfos = Collections.singletonList(orderInfoMapper.selectById(id));
        }
        List<OrderDTO> dtoList = orderInfos.stream()
                                           .map(t -> {
                                               OrderDTO orderDTO = new OrderDTO();
                                               copy(orderDTO, t);
                                               return orderDTO;
                                           })
                                           .collect(Collectors.toList());
        return RestRes.ok(new PageInfo<>(dtoList));
    }

    @Override
    public RestRes<List<OrderDetailDTO>> getOrderTOList(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JwtConsts.JWT_TOKEN_HEAD).substring(7);
        Long uid = Long.parseLong(JwtUtils.parseToken(token, JwtConsts.JWT_TOKEN_KEY));
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("user_id",uid);
        List<OrderInfo> orderInfos = orderInfoMapper.selectList(orderInfoQueryWrapper);
        List<Long> goods = orderInfos.stream()
                                       .map(OrderInfo::getGoodsId)
                                       .collect(Collectors.toList());

        List<String> goodsList = goodsMapper.selectByIdsSortById(goods);
        AtomicInteger goodIdIndex = new AtomicInteger(0);
        List<OrderDetailDTO> dtoList = orderInfos.stream().sorted(Comparator.comparing(OrderInfo::getGoodsId))
                                                 .map(t -> {
                                                     OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                                                     copy(orderDetailDTO, t);
                                                     String goodName = goodsList.get(goodIdIndex.getAndIncrement());
                                                     orderDetailDTO.setGoodsName(goodName);
                                                     orderDetailDTO.setOrderId(t.getId());
                                                     return orderDetailDTO;
                                                 })
                                                 .collect(Collectors.toList());
        return RestRes.ok(dtoList);
    }
}
