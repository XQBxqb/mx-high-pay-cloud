package cn.high.mx.module.mission.dataobj;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
* 库存表
* </p>
*
* @author  MX 
* @since 2023-12-03
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = false)
@TableName("seckill_goods")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeckillGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("goods_id")
    private Long goodsId;

    @TableField("stock_count")
    private Integer stockCount;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;


    public SeckillGoods(Long goodsId, Integer stockCount) {
        this.goodsId = goodsId;
        this.stockCount = stockCount;
    }
}
