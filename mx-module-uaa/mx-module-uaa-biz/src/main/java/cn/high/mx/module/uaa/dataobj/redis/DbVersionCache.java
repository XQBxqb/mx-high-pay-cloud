package cn.high.mx.module.uaa.dataobj.redis;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
* @since 2023-11-28
*/
@Data

public class DbVersionCache implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer installedRank;

    private String version;

    private String description;

    private String type;

    private String script;

    private Integer checksum;

    private String installedBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime installedOn;

    private Integer executionTime;

    private Boolean success;


}
