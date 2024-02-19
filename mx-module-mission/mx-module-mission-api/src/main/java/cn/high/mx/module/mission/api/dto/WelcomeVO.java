package cn.high.mx.module.mission.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WelcomeVO {
    // 商品数
    private int goodsCount;
    // 秒杀商品数
    private int seckillCount;
    // 订单数
    private int orderCount;

}
