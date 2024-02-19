package cn.gene.pojo;

    import com.baomidou.mybatisplus.annotation.TableName;
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
    * 订单表
    * </p>
*
* @author  MX 
* @since 2023-12-03
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("order_info")
    @ApiModel(value="OrderInfo对象", description="订单表")
    public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "主键")
            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            @ApiModelProperty(value = "用户id")
        @TableField("user_id")
    private Long userId;

            @ApiModelProperty(value = "商品id")
        @TableField("goods_id")
    private Long goodsId;

            @ApiModelProperty(value = "创建时间")
        @TableField("created_at")
    private LocalDateTime createdAt;

            @ApiModelProperty(value = "更新时间")
        @TableField("updated_at")
    private LocalDateTime updatedAt;


}
