package cn.high.mx.module.manager.framework.shiro.dto;



import cn.high.mx.module.manager.framework.shiro.core.base.BasePermission;
import cn.high.mx.module.manager.framework.shiro.core.base.BaseRole;
import cn.high.mx.module.manager.framework.shiro.core.base.BaseRolePermission;
import lombok.Data;

import java.io.Serializable;


@Data
public class RolePermissionSH implements BaseRolePermission, Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 权限ID
     */
    private Integer permissionId;

    /**
     * 角色信息
     */
    private RoleSH roleSH;

    /**
     * 权限信息
     */
    private PermissionSH permissionSH;

    @Override
    public BaseRole getBaseRole() {
        return roleSH;
    }

    @Override
    public void setBaseRole(BaseRole baseRole) {
        setRoleSH((RoleSH) baseRole);
    }

    @Override
    public BasePermission getBasePermission() {
        return getPermissionSH();
    }

    @Override
    public void setBasePermission(BasePermission basePermission) {
        setPermissionSH((PermissionSH) basePermission);
    }
}
