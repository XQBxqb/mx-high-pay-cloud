package cn.high.mx.module.mission.service.impl;

import cn.high.mx.module.mission.api.dto.WelcomeVO;
import cn.high.mx.module.mission.dao.GoodsMapper;
import cn.high.mx.module.mission.dao.OrderInfoMapper;
import cn.high.mx.module.mission.dao.SeckillGoodsMapper;
import cn.high.mx.module.mission.res.RestRes;
import cn.high.mx.module.mission.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeServiceImpl implements HomeService {
    private final OrderInfoMapper orderInfoMapper;

    private final GoodsMapper goodsMapper;

    private final SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public RestRes<WelcomeVO> welcome() {
        int orderCounts = orderInfoMapper.counts();
        int goodsCounts = goodsMapper.counts();
        int seckillCounts = seckillGoodsMapper.counts();
        return RestRes.ok(WelcomeVO.builder()
                                   .goodsCount(goodsCounts)
                                   .orderCount(orderCounts)
                                   .seckillCount(seckillCounts)
                                   .build());
    }
}
