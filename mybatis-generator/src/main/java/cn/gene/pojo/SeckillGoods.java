package cn.gene.pojo;

    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

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
    @Accessors(chain = true)
    @TableName("seckill_goods")
    @ApiModel(value="SeckillGoods对象", description="库存表")
    public class SeckillGoods implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "商品id")
            @TableId("goods_id")
    private Long goodsId;

            @ApiModelProperty(value = "剩余库存")
        @TableField("stock_count")
    private Integer stockCount;

            @ApiModelProperty(value = "创建时间")
        @TableField("created_at")
    private LocalDateTime createdAt;

            @ApiModelProperty(value = "更新时间")
        @TableField("updated_at")
    private LocalDateTime updatedAt;


}
