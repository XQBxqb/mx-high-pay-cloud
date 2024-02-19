package cn.high.mx.module.manager.dataobj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
* 权限菜单表
* </p>
*
* @author  MX 
* @since 2023-11-26
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = false)
@TableName("permission_menu")
public class PermissionMenu implements Serializable {

private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer level;

    private Integer sort;

    @TableField("permission_id")
    private Integer permissionId;

    @TableField("parent_id")
    private String parentId;

    private String key;

    private String name;

    private String icon;


}
