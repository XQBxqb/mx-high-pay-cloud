package cn.high.mx.module.mission.dataobj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**

* @author  MX 
* @since 2023-12-03
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("goods_name")
    private String goodsName;

    @TableField("goods_img")
    private String goodsImg;

    @TableField("is_using")
    private Boolean isUsing;

    @TableField("goods_title")
    private String goodsTitle;

    @TableField("goods_price")
    private BigDecimal goodsPrice;

    @TableField("goods_stock")
    private Integer goodsStock;

    @TableField("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @TableField("end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @TableField("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

}
