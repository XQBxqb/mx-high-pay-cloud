package cn.high.mx.module.manager.framework.shiro.dto;




import cn.high.mx.module.manager.framework.shiro.core.base.BasePermission;
import lombok.Data;

import java.io.Serializable;

@Data
public class PermissionSH implements BasePermission, Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    /**
     * 权限
     */
    private String permission;

    /**
     * 权限名称
     */
    private String permissionName;

}
