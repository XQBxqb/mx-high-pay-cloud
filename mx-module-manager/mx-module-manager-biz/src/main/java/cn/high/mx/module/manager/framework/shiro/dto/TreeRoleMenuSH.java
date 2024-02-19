package cn.high.mx.module.manager.framework.shiro.dto;



import cn.high.mx.module.manager.framework.shiro.core.base.BaseTreeRoleMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class TreeRoleMenuSH implements BaseTreeRoleMenu, Serializable {
    private static final long serialVersionUID = 1L;
    private Integer value;

    private Integer parentId;

    private Integer level;

    private String key;

    /**
     * 名称
     */
    private String title;

    public List<TreeRoleMenuSH> children ;

    @Override
    public void setChildren(List<BaseTreeRoleMenu> children) {
        setChildren(children.stream().map(t->(TreeRoleMenuSH)t).collect(Collectors.toList()));
    }
    @Override
    public List<BaseTreeRoleMenu> getChildren(){
        return getChildren().stream().map(t->(BaseTreeRoleMenu)t).collect(Collectors.toList());
    }
}
