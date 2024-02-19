package cn.high.mx.module.manager.framework.shiro.dto;


;
import cn.high.mx.module.manager.framework.shiro.core.base.BaseAdminUser;
import cn.high.mx.module.manager.framework.shiro.core.base.BasePermission;
import cn.high.mx.module.manager.framework.shiro.core.base.BaseRole;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AdminUserSH implements BaseAdminUser, Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String username;

    private String password;
    private Boolean isBan;
    private String phone;
    private String name;
    private String avatar;
    private String role;

    private List<RoleSH> roleSHES;

    private List<PermissionSH> permissionSHES;

    @Override
    public List<BaseRole> getBaseRoles() {
        return getRoleSHES().stream().map(t->(BaseRole)t).collect(Collectors.toList());
    }

    @Override
    public void setBaseRoles(List<BaseRole> roles) {
        setRoleSHES(roles.stream().map(t->(RoleSH)t).collect(Collectors.toList()));
    }

    @Override
    public List<BasePermission> getBasePermissions() {
        return getPermissionSHES().stream().map(t->(BasePermission)t).collect(Collectors.toList());
    }

    @Override
    public void setBasePermissions(List<BasePermission> permissions) {
        setPermissionSHES(permissions.stream().map(t->(PermissionSH)t).collect(Collectors.toList()));
    }

    public void setRole(String role) {
        this.role = role;
    }
}
