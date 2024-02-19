package cn.high.mx.module.manager.framework.shiro.core.base;

import java.util.List;


public interface BasePermissionMenu {


    public Integer getId();

    public void setId(Integer id);

    public Integer getLevel();

    public void setLevel(Integer level);

    public Integer getSort() ;


    public void setSort(Integer sort);

    public Integer getPermissionId() ;

    public void setPermissionId(Integer permissionId);

    public Integer getParentId() ;

    public void setParentId(Integer parentId) ;

    public String getKey() ;

    public void setKey(String key);

    public String getName() ;

    public void setName(String name);

    public String getIcon() ;

    public void setIcon(String icon);

    public BasePermission getBasePermission() ;

    public void setBasePermission(BasePermission permission) ;

    public List<BasePermissionMenu> getBaseChildren() ;

    public void setBaseChildren(List<BasePermissionMenu> children) ;

}
