package cn.high.mx.module.mission.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class OrderDetailDTO {

    private Long orderId;

    private Long goodsId;

    private String goodsName;

    private LocalDateTime createdAt;

}
