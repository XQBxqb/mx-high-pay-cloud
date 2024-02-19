package cn.high.mx.module.mission.api.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsDTO {

    private Long id;

    @NotBlank(message = "商品名称是必须的")
    private String goodsName;

    private String goodsImg;
    @NotNull(message = "是否启用是必须的")
    private Boolean isUsing;
    @NotBlank(message = "商品标题是必须的")
    private String goodsTitle;
    @NotNull(message = "商品价格是必须的")
    private BigDecimal goodsPrice;
    @NotNull(message = "商品库存是必须的")
    private Integer goodsStock;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "秒杀开始时间是必须的")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "秒杀结束时间是必须的")
    private LocalDateTime endTime;
}
