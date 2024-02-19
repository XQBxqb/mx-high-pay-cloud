package cn.high.mx.module.manager.framework.shiro.dto;


import cn.high.mx.module.manager.framework.shiro.core.base.BasePermission;
import cn.high.mx.module.manager.framework.shiro.core.base.BaseRole;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class RoleSH implements BaseRole, Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    /**
     * 角色
     */
    private String role;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 拥有权限
     */
    private List<PermissionSH> permissionSHES;


    @Override
    public List<BasePermission> getPermissions() {
        return getPermissions().stream().map(t->(BasePermission)t).collect(Collectors.toList());
    }

    @Override
    public void setBasePermissions(List<BasePermission> basePermissions) {
        this.permissionSHES = basePermissions.stream().map(t-> (PermissionSH)t).collect(Collectors.toList());
    }
}
