package cn.high.mx.module.manager.framework.shiro.core.base;


public interface BaseRolePermission {

    public Integer getId();

    public void setId(Integer id);

    public Integer getRoleId() ;

    public void setRoleId(Integer roleId);

    public Integer getPermissionId();

    public void setPermissionId(Integer permissionId);

    public BaseRole getBaseRole() ;

    public void setBaseRole(BaseRole baseRole) ;

    public BasePermission getBasePermission() ;

    public void setBasePermission(BasePermission basePermission);


}
