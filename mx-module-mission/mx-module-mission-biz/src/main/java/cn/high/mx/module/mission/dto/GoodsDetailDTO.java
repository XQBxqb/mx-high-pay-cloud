package cn.high.mx.module.mission.dto;

import cn.high.mx.module.mission.dataobj.Goods;
import lombok.Data;

@Data
public class GoodsDetailDTO {

    private int remainSeconds = 0;

    private int stockCount;

    private Goods goods;
}
