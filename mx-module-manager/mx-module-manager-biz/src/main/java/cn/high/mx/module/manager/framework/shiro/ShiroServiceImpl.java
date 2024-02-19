package cn.high.mx.module.manager.framework.shiro;

import cn.high.mx.module.manager.dto.AdminUserDTO;
import cn.high.mx.module.manager.dto.PermissionDTO;
import cn.high.mx.module.manager.dto.RoleDTO;
import cn.high.mx.module.manager.framework.shiro.core.BaseShiroService;
import cn.high.mx.module.manager.framework.shiro.core.base.BaseAdminUser;
import cn.high.mx.module.manager.framework.shiro.core.base.BasePermission;
import cn.high.mx.module.manager.framework.shiro.core.base.BaseRole;
import cn.high.mx.module.manager.framework.shiro.config.JwtFilter;
import cn.high.mx.module.manager.framework.shiro.dto.AdminUserSH;
import cn.high.mx.module.manager.framework.shiro.dto.PermissionSH;
import cn.high.mx.module.manager.framework.shiro.dto.RoleSH;
import cn.high.mx.module.manager.dataobj.Permission;
import cn.high.mx.module.manager.service.AdminUserService;
import cn.high.mx.module.manager.service.PermissionService;
import cn.high.mx.module.manager.service.RolePermissionService;
import cn.high.mx.module.manager.service.RoleService;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.Filter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShiroServiceImpl implements BaseShiroService {
    @Autowired
    private AdminUserService adminUserService ;

    @Autowired
    private PermissionService permissionService ;
    @Autowired
    private RolePermissionService rolePermissionService ;
    @Autowired
    private RoleService roleService ;

    @Override
    public Boolean isExistUser(Long userId) {
        return false;
    }

    @Override
    public Map<String, Filter> getFilterMap() {
        HashMap<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt",new JwtFilter());
        return filterMap;
    }

    @Override
    public Map<String, String> getFilterRuleMap() {
        HashMap<String, String> filterRuleMap = new HashMap<>();
        filterRuleMap.put("/login","anon");
        filterRuleMap.put("/**/demo/**","anon");
        filterRuleMap.put("/actuator/**","anon");
        filterRuleMap.put("/session/findByUsername","anon");
        filterRuleMap.put("/swagger-ui/**","anon");
        filterRuleMap.put("/swagger-resources/**","anon");
        filterRuleMap.put("/v3/**","anon");
        filterRuleMap.put("/error","anon");
        filterRuleMap.put("/session","jwt");
        filterRuleMap.put("/**", "jwt");
        return filterRuleMap;
    }

    @Override
    @SneakyThrows
    public BaseAdminUser getAdminUserByName(String username) {
        AdminUserDTO adminUsers = adminUserService.getByName(username);
        if(adminUsers == null){
            return null;
        }
        String role = adminUsers.getRole();
        AdminUserSH adminUserSH = new AdminUserSH();
        BeanUtils.copyProperties(adminUserSH,adminUsers);
        return adminUserSH;
    }

    @Override
    @SneakyThrows
    public List<BaseRole> getRoleIdByRoleName(String roleName) {
        List<RoleDTO> roleList = roleService.getByName(roleName);
        List<BaseRole> roles = roleList.stream()
                                        .map(t->getRoleDTO(t))
                                        .collect(Collectors.toList());
        return roles;
    }

    private static BaseRole getRoleDTO(RoleDTO t) {
        BaseRole baseRole = null;
        try {
            baseRole = new RoleSH();
            BeanUtils.copyProperties(baseRole, t);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return baseRole;
    }

    @Override
    public List<BasePermission> getPermissionByRole(Integer roleId) {
        List<Integer> idList = rolePermissionService.getByRoleId(roleId)
                                                    .stream()
                                                    .map(t -> Integer.valueOf(t))
                                                    .collect(Collectors.toList());
        List<PermissionDTO> permissions = permissionService.getListByIds(idList);
        List<BasePermission> basePermissionList = permissions.stream()
                                                             .map(t ->getBasePermission(t))
                                                             .collect(Collectors.toList());
        return basePermissionList;
    }

    private static BasePermission getBasePermission(PermissionDTO t)  {
        BasePermission permission = null;
        try {
            permission = new PermissionSH();
            BeanUtils.copyProperties(permission, t);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return permission;
    }

    public List<Permission> getPermissionListById(List<Integer> permissionIdList) {
        return null;
    }
}
