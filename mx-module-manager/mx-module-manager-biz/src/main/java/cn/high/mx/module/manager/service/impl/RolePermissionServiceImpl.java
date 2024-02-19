package cn.high.mx.module.manager.service.impl;


import cn.high.mx.module.manager.dao.RolePermissionMapper;
import cn.high.mx.module.manager.service.BaseService;
import cn.high.mx.module.manager.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl extends BaseService implements RolePermissionService {
    private final RolePermissionMapper rolePermissionMapper;

    @Override
    public List<String> getByRoleId(Integer roleId) {
        return rolePermissionMapper.selectPermissionIdList(roleId);
    }
}
