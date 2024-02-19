package cn.high.mx.module.manager.framework.shiro.core;


import cn.high.mx.module.manager.framework.shiro.core.base.BaseAdminUser;
import cn.high.mx.module.manager.framework.shiro.core.base.BasePermission;
import cn.high.mx.module.manager.framework.shiro.core.base.BaseRole;

import javax.servlet.Filter;
import java.util.List;
import java.util.Map;

public interface BaseShiroService {
    public Boolean isExistUser(Long userId);

    public Map<String, Filter> getFilterMap();

    public Map<String, String> getFilterRuleMap();

    public BaseAdminUser getAdminUserByName(String username);

    public List<BaseRole> getRoleIdByRoleName(String roleName);

    public List<BasePermission> getPermissionByRole(Integer roleId);
}
