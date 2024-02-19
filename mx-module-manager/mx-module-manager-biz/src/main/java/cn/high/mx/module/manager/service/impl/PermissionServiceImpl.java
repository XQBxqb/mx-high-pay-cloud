package cn.high.mx.module.manager.service.impl;


import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.manager.dao.PermissionMapper;
import cn.high.mx.module.manager.dataobj.Permission;
import cn.high.mx.module.manager.dto.PermissionDTO;
import cn.high.mx.module.manager.dto.ReqPermission;
import cn.high.mx.module.manager.service.BaseService;
import cn.high.mx.module.manager.service.PermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends BaseService implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionDTO> getListByIds(List<Integer> ids) {
        return permissionMapper.queryPermissionListByIdList(ids)
                               .stream()
                               .map(t -> {
                                   PermissionDTO permissionDTO = new PermissionDTO();
                                   copy(permissionDTO, t);
                                   return permissionDTO;
                               })
                               .collect(Collectors.toList());
    }

    @Override
    public RestRes<List<PermissionDTO>> findAll() {
        List<Permission> permissions = permissionMapper.selectAll();
        List<PermissionDTO> permissionDTOS = doToDtos(permissions);
        return RestRes.ok(permissionDTOS);
    }

    @Override
    public RestRes<PageInfo<PermissionDTO>> findByName(Integer page, Integer pageSize, String name) {
        PageHelper.startPage(page,pageSize);
        List<Permission> permissionList = StringUtils.isBlank(name) ? permissionMapper.selectAll() : permissionMapper.findByLike(name);
        List<PermissionDTO> permissionDTOS = doToDtos(permissionList);
        return RestRes.ok(new PageInfo<>(permissionDTOS));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Object> deletes(String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                                   .map(Integer::valueOf)
                                   .collect(Collectors.toList());
        permissionMapper.deleteList(idList);
        return RestRes.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Object> addPermission(ReqPermission reqPermission) {
        Permission permission = new Permission();
        copy(permission, reqPermission);
        permissionMapper.insert(permission);
        return RestRes.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Object> updatePermission(ReqPermission reqPermission) {
        Permission permission = new Permission();
        copy(permission, reqPermission);
        permissionMapper.updateById(permission);
        return RestRes.ok();
    }

    private List<PermissionDTO> doToDtos(List<Permission> permissions) {
        return permissions.stream()
                          .map(t -> {
                              PermissionDTO permissionDTO = new PermissionDTO();
                              copy(permissionDTO, t);
                              return permissionDTO;
                          })
                          .collect(Collectors.toList());
    }
}
