package cn.gene.pojo;

    import java.math.BigDecimal;
    import com.baomidou.mybatisplus.annotation.IdType;
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
    * 商品表
    * </p>
*
* @author  MX 
* @since 2023-12-03
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @ApiModel(value="Goods对象", description="商品表")
    public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "主键")
            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            @ApiModelProperty(value = "商品名称")
        @TableField("goods_name")
    private String goodsName;

            @ApiModelProperty(value = "商品图片")
        @TableField("goods_img")
    private String goodsImg;

            @ApiModelProperty(value = "是否启用")
        @TableField("is_using")
    private Boolean isUsing;

            @ApiModelProperty(value = "商品标题")
        @TableField("goods_title")
    private String goodsTitle;

            @ApiModelProperty(value = "商品价格")
        @TableField("goods_price")
    private BigDecimal goodsPrice;

            @ApiModelProperty(value = "商品库存")
        @TableField("goods_stock")
    private Integer goodsStock;

            @ApiModelProperty(value = "秒杀开始时间")
        @TableField("start_time")
    private LocalDateTime startTime;

            @ApiModelProperty(value = "秒杀结束时间")
        @TableField("end_time")
    private LocalDateTime endTime;

            @ApiModelProperty(value = "创建时间")
        @TableField("created_at")
    private LocalDateTime createdAt;

            @ApiModelProperty(value = "更新时间")
        @TableField("updated_at")
    private LocalDateTime updatedAt;


}
