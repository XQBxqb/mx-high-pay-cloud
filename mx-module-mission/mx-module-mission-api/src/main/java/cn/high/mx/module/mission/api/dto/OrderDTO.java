package cn.high.mx.module.mission.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {
    // 主键
    private Long id;

    // 用户id
    private Long userId;

    // 商品id
    private Long goodsId;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createdAt;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime updatedAt;
}
