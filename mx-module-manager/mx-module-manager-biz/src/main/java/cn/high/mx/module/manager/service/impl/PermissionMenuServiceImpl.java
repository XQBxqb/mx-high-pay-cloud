package cn.high.mx.module.manager.service.impl;


import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.manager.dao.PermissionMapper;
import cn.high.mx.module.manager.dao.PermissionMenuMapper;
import cn.high.mx.module.manager.dataobj.Permission;
import cn.high.mx.module.manager.dataobj.PermissionMenu;
import cn.high.mx.module.manager.dto.PermissionDTO;
import cn.high.mx.module.manager.dto.PermissionMenuDTO;
import cn.high.mx.module.manager.dto.MenuDTO;
import cn.high.mx.module.manager.dto.TreeRoleMenuDTO;
import cn.high.mx.module.manager.service.BaseService;
import cn.high.mx.module.manager.service.PermissionMenuService;
import cn.high.mx.module.manager.utils.local.ThreadLocalData;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionMenuServiceImpl extends BaseService implements PermissionMenuService {
    private final PermissionMenuMapper permissionMenuMapper;

    private final PermissionMapper permissionMapper;

    @Override
    public RestRes<List<PermissionMenuDTO>> getMenu() {
        String ids = ThreadLocalData.getThreadLocal(ThreadLocalData.PERMISSIONS);
        List<Integer> idList = Arrays.stream(ids.split(","))
                                     .map(Integer::valueOf)
                                     .collect(Collectors.toList());
        List<PermissionMenu> permissionMenus = permissionMenuMapper.selectByPermissionIds(idList);
        List<PermissionMenuDTO> permissionMenuDTOS = new ArrayList<>();
        for(PermissionMenu permissionMenu : permissionMenus){
            if(permissionMenu.getParentId() != null)
                continue;
            PermissionMenuDTO permissionMenuDTO = getPermissionMenuDTO(permissionMenus, permissionMenu);
            permissionMenuDTOS.add(permissionMenuDTO);
        }
        return RestRes.ok(permissionMenuDTOS);
    }

    private PermissionMenuDTO getPermissionMenuDTO(List<PermissionMenu> permissionMenus, PermissionMenu permissionMenu) {
        PermissionMenuDTO permissionMenuDTO = new PermissionMenuDTO();
        copy(permissionMenuDTO, permissionMenu);
        List<PermissionMenuDTO> childList = new ArrayList<>();
        for(PermissionMenu otherPermissionMenu: permissionMenus){
            if(otherPermissionMenu.getParentId() !=null && otherPermissionMenu.getParentId().equals(String.valueOf(permissionMenu.getId()))){
                PermissionMenuDTO childDto = new PermissionMenuDTO();
                copy(childDto,otherPermissionMenu);
                Permission permission = permissionMapper.selectById(childDto.getPermissionId());
                PermissionDTO permissionDTO = new PermissionDTO();
                copy(permissionDTO,permission);
                childDto.setPermission(permissionDTO);
                childDto.setChildren(new ArrayList<>());
                childList.add(childDto);
            }
        }
        Permission permission = permissionMapper.selectById(permissionMenu.getPermissionId());
        PermissionDTO permissionDTO = new PermissionDTO();
        copy(permissionDTO,permission);

        permissionMenuDTO.setPermission(permissionDTO);
        permissionMenuDTO.setChildren(childList);
        return permissionMenuDTO;
    }

    @Override
    public RestRes<List<TreeRoleMenuDTO>> getAll() {
        List<TreeRoleMenuDTO> menuDTOS = parseTreeRoleMenu(permissionMenuMapper.selectMenuList());
        return RestRes.ok(menuDTOS);
    }

    @Override
    public RestRes<List<PermissionMenuDTO>> findByRoleMenus(String search) {
        List<PermissionMenu> list;
        list = StringUtils.isBlank(search) ? permissionMenuMapper.selectAll() : permissionMenuMapper.findByLike(search);
        List<PermissionMenuDTO> permissionMenuDTOS = boToDtos(list);
        return RestRes.ok(permissionMenuDTOS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Object> updateMenu(MenuDTO menuDTO) {
        PermissionMenu permissionMenu = new PermissionMenu();
        copy(permissionMenu, menuDTO);
        permissionMenuMapper.updateById(permissionMenu);
        return RestRes.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Object> deleteById(String id) {
        permissionMenuMapper.deleteById(id);
        return RestRes.ok();
    }

    @Override
    @Transactional(rollbackFor =  Exception.class)
    public RestRes<Object> addMenu(MenuDTO menuDTO) {
        PermissionMenu permissionMenu = new PermissionMenu();
        copy(permissionMenu, menuDTO);
        permissionMenuMapper.insert(permissionMenu);
        return RestRes.ok();
    }

    private List<PermissionMenuDTO> boToDtos(List<PermissionMenu> list) {
        return list.stream()
                   .map(t -> {
                       PermissionMenuDTO permissionMenuDTO = new PermissionMenuDTO();
                       copy(permissionMenuDTO, t);
                       return permissionMenuDTO;
                   })
                   .collect(Collectors.toList());
    }

    private static List<TreeRoleMenuDTO> parseTreeRoleMenu(List<TreeRoleMenuDTO> treeRoleMenuDTOS) {
        for (int i = 0; i < treeRoleMenuDTOS.size(); i++) {
            TreeRoleMenuDTO treeRoleMenuDTO = treeRoleMenuDTOS.get(i);
            Integer parentId;
            if ((parentId = treeRoleMenuDTO.getParentId()) == null) continue;
            List<TreeRoleMenuDTO> childrenRole;
            for (TreeRoleMenuDTO parentTree : treeRoleMenuDTOS) {
                if (parentTree.getValue()
                              .equals(parentId)) {
                    childrenRole = parentTree.getChildren();
                    if (childrenRole == null) {
                        childrenRole = new ArrayList<>();
                        parentTree.setChildren(childrenRole);
                    }
                    childrenRole.add(treeRoleMenuDTO);
                    break;
                }
            }
        }
        List<TreeRoleMenuDTO> menuDTOS = treeRoleMenuDTOS.stream()
                                                         .filter(t -> t.getChildren() == null)
                                                         .sorted(Comparator.comparing(TreeRoleMenuDTO::getValue))
                                                         .collect(Collectors.toList());
        return menuDTOS;
    }
}
