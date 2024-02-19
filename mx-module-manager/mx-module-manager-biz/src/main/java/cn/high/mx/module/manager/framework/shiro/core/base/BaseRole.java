package cn.high.mx.module.manager.framework.shiro.core.base;



import java.util.List;


public interface BaseRole {

    public Integer getId() ;

    public void setId(Integer id);

    public String getRole() ;

    public void setRole(String role) ;

    public String getRoleName() ;

    public void setRoleName(String roleName) ;
    public List<BasePermission> getPermissions() ;

    public void setBasePermissions(List<BasePermission> basePermissions) ;
}
