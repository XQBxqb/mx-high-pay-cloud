package cn.high.mx.module.manager.framework.shiro.core.base;

import java.util.List;



public interface BaseTreeRoleMenu {


    public Integer getValue();

    public void setValue(Integer value) ;

    public Integer getParentId() ;

    public void setParentId(Integer parentId);

    public Integer getLevel() ;

    public void setLevel(Integer level);

    public String getKey();

    public void setKey(String key);


    public String getTitle() ;


    public void setTitle(String title);

    public List<BaseTreeRoleMenu> getChildren();

    public void setChildren(List<BaseTreeRoleMenu> children);




}
