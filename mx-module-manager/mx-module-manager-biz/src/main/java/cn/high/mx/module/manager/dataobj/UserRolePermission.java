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
* 用户角色权限表
* </p>
*
* @author  MX 
* @since 2023-11-26
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = false)
@TableName("user_role_permission")
public class UserRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("role_id")
    private Integer roleId;

    @TableField("permission_id")
    private Integer permissionId;


}
