package cn.gene.pojo;

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
    * 客户表
    * </p>
*
* @author  MX 
* @since 2023-11-28
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @ApiModel(value="User对象", description="客户表")
    public class User implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            @ApiModelProperty(value = "登录手机号")
    private String phone;

            @ApiModelProperty(value = "密码")
    private String password;

            @ApiModelProperty(value = "创建时间")
        @TableField("created_at")
    private LocalDateTime createdAt;

            @ApiModelProperty(value = "更新时间")
        @TableField("updated_at")
    private LocalDateTime updatedAt;

            @ApiModelProperty(value = "最后一次登录时间")
        @TableField("last_login_time")
    private LocalDateTime lastLoginTime;


}
