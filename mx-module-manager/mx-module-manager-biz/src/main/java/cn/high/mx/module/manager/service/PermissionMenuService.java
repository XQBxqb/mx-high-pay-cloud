package cn.high.mx.module.manager.service;

import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.manager.dto.PermissionMenuDTO;
import cn.high.mx.module.manager.dto.MenuDTO;
import cn.high.mx.module.manager.dto.TreeRoleMenuDTO;

import java.util.List;

public interface PermissionMenuService {
    public RestRes<List<PermissionMenuDTO>> getMenu();

    public RestRes<List<TreeRoleMenuDTO>> getAll();

    public RestRes<List<PermissionMenuDTO>> findByRoleMenus(String search);

    public RestRes<Object> updateMenu(MenuDTO menuDTO);

    public RestRes<Object> deleteById(String id);

    public RestRes<Object> addMenu(MenuDTO menuDTO);

}
