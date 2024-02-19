package cn.high.mx.module.manager.framework.shiro.core.base;


public interface BasePermission {


    public Integer getId() ;


    public String getPermission();


    public String getPermissionName();


    public void setId(Integer id);


    public void setPermission(String permission) ;


    public void setPermissionName(String permissionName);
}
