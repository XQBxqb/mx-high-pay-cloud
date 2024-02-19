package cn.high.mx.module.manager.framework.shiro.dto;



import cn.high.mx.module.manager.framework.shiro.core.base.BasePermission;
import cn.high.mx.module.manager.framework.shiro.core.base.BasePermissionMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class PermissionMenuSH implements BasePermissionMenu, Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    /**
     * 一级还是二级菜单
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 权限Id
     */
    private Integer permissionId;



    /**
     * 父级ID
     */
    private Integer parentId;

    /**
     * 组件页面名
     */
    private String key;

    /**
     * 名称
     */
    private String name;

    /**
     *  图标 设置该路由的图标
     */
    private String icon;

    /**
     * 权限
     */
    private PermissionSH permissionSH;

    public List<PermissionMenuSH> children = null;


    @Override
    public BasePermission getBasePermission() {
        return permissionSH;
    }

    @Override
    public void setBasePermission(BasePermission permission) {
        this.permissionSH = (PermissionSH) permission;
    }

    @Override
    public List<BasePermissionMenu> getBaseChildren() {
        return this.getChildren().stream().map(t->(BasePermissionMenu)t).collect(Collectors.toList());
    }

    @Override
    public void setBaseChildren(List<BasePermissionMenu> children) {
        this.children = children.stream().map(t->(PermissionMenuSH) children).collect(Collectors.toList());
    }
}
