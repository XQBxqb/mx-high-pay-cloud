package cn.high.mx.module.manager.service.impl;

import cn.high.mx.framework.common.exception.enums.BizStatusEnum;
import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.manager.dao.AdminUsersMapper;
import cn.high.mx.module.manager.dao.RoleMapper;
import cn.high.mx.module.manager.dao.RolePermissionMapper;
import cn.high.mx.module.manager.dao.UserRolePermissionMapper;
import cn.high.mx.module.manager.dataobj.Role;
import cn.high.mx.module.manager.dataobj.RolePermission;
import cn.high.mx.module.manager.dto.ReqRole;
import cn.high.mx.module.manager.dto.ResRole;
import cn.high.mx.module.manager.dto.RoleDTO;
import cn.high.mx.module.manager.service.BaseService;
import cn.high.mx.module.manager.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseService implements RoleService {
    private final AdminUsersMapper adminUsersMapper;
    private final RoleMapper roleMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final UserRolePermissionMapper userRolePermissionMapper;

    @Override
    public List<RoleDTO> getByName(String roleName) {
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("role", roleName);
        List<Role> roles = roleMapper.selectList(roleQueryWrapper);
        List<RoleDTO> roleDTOS = roles.stream()
                                      .map(t -> {
                                          RoleDTO roleDTO = new RoleDTO();
                                          copy(roleDTO, t);
                                          return roleDTO;
                                      })
                                      .collect(Collectors.toList());
        return roleDTOS;
    }

    @Override
    public RestRes<List<ResRole>> getAll() {
        List<ResRole> resRoles = roleMapper.selectRoleDTOs();
        return RestRes.ok(resRoles);
    }

    @Override
    public RestRes<PageInfo<ResRole>> findBySearch(Integer page, Integer pageSize, String search) {
        PageHelper.startPage(page,pageSize);
        List<ResRole> roles;
        roles = StringUtils.isBlank(search) ? roleMapper.selectRoleDTOs() : roleMapper.selectRoleDTOsBySearch(search);
        return RestRes.ok(new PageInfo<>(roles));
    }

    /**
     * 更新角色，首先更新拥有该角色对应用户的role字段，然后role表先进行更新，
     * 最后role_permission表先删除过去所有相连数据，然后放入新的数据
     * @param roleReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Object> update(ReqRole roleReq) {
        Integer roleId = roleReq.getId();
        Role role = new Role();
        copy(role, roleReq);
        roleMapper.updateById(role);
        String oldRole = roleMapper.selectOldRoleById(roleId);
        adminUsersMapper.updateRole(roleReq.getRole(),oldRole);
        QueryWrapper<RolePermission> rolePermissionQueryWrapper = new QueryWrapper<>();
        rolePermissionQueryWrapper.eq("role_id", roleId);
        rolePermissionMapper.delete(rolePermissionQueryWrapper);
        rolePermissionMapper.batchInsert(roleId, Arrays.asList(roleReq.getPermissionIds()));
        return RestRes.ok();
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Object> deletes(String ids) {
        List<Integer> roleIds = Arrays.stream(ids.split(","))
                                      .map(Integer::valueOf)
                                      .collect(Collectors.toList());
        AtomicBoolean isExitUserOwnRole = new AtomicBoolean(false);
        roleIds.forEach(t->{
            Integer integer = userRolePermissionMapper.selectCountHasRole(t);
            if(integer != null || integer != 0){
                isExitUserOwnRole.set(true);
            }
        });
        if(isExitUserOwnRole.get())
            return RestRes.errorEnum(BizStatusEnum.RES_ERROR_EXIT_USER_OWN_DELETE_ROLE);
        rolePermissionMapper.deletes(roleIds);
        roleMapper.deletes(roleIds);
        return RestRes.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Object> addRole(ReqRole reqRole) {
        Role role = new Role();
        copy(role,reqRole);
        roleMapper.insert(role);
        rolePermissionMapper.insertList(reqRole.getId(), Arrays.stream(reqRole.getPermissionIds()).collect(Collectors.toList()));
        return RestRes.ok();
    }
    public void copy(Object dest, Object orig) {
        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
