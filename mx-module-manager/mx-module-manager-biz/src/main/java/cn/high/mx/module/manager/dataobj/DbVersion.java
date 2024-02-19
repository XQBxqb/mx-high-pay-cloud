package cn.high.mx.module.manager.dataobj;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
*
* </p>
*
* @author  MX 
* @since 2023-11-26
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = false)
@TableName("t_db_version")
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
