package cn.high.mx.module.manager.dataobj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
* 权限表
* </p>
*
* @author  MX 
* @since 2023-11-26
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = false)
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String permission;

    @TableField("permission_name")
    private String permissionName;


}
