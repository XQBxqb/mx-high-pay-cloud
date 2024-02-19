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
    * 
    * </p>
*
* @author  MX 
* @since 2023-11-28
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("t_db_version")
    @ApiModel(value="DbVersion对象", description="")
    public class DbVersion implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId("installed_rank")
    private Integer installedRank;

    private String version;

    private String description;

    private String type;

    private String script;

    private Integer checksum;

        @TableField("installed_by")
    private String installedBy;

        @TableField("installed_on")
    private LocalDateTime installedOn;

        @TableField("execution_time")
    private Integer executionTime;

    private Boolean success;


}
