package cn.high.mx.module.mission.api.dto;

import lombok.Data;

@Data
public class SeckillGoodsDTO {
    // 商品id
    private Long goodsId;

    // 剩余库存
    private int stockCount;

}
