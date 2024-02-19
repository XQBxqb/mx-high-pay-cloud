package cn.high.mx.module.manager.framework.shiro.core.base;

import java.util.List;

public interface BaseAdminUser {

    public Integer getId();


    public void setId(Integer id);


    public String getUsername();


    public void setUsername(String username) ;


    public String getPassword();


    public void setPassword(String password);


    public Boolean getIsBan();

    public void setIsBan(Boolean isBan);

    public String getPhone();


    public void setPhone(String phone);


    public String getName() ;



    public void setName(String name);


    public String getAvatar();



    public void setAvatar(String avatar);


    public String getRole() ;


    public void setRole(String role);

    public List<BaseRole> getBaseRoles();

    public void setBaseRoles(List<BaseRole> roles);

    public List<BasePermission> getBasePermissions() ;

    public void setBasePermissions(List<BasePermission> permissions);

}
