package cn.high.mx.module.manager.controller.admin;

import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.manager.dto.PermissionMenuDTO;
import cn.high.mx.module.manager.dto.MenuDTO;
import cn.high.mx.module.manager.dto.TreeRoleMenuDTO;
import cn.high.mx.module.manager.service.PermissionMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色菜单管理")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/roleMenu")
public class PermissionMenuController {
    private final PermissionMenuService permissionMenuService;

    @ApiOperation(value = "获取所有菜单")
    @GetMapping("/getMenus")
    @RequiresPermissions("MENU_ADMIN_USER")
    public RestRes<List<TreeRoleMenuDTO>> getMenus() {
        return permissionMenuService.getAll();
    }

    /**
     * 菜单列表(目录/菜单)
     */
    @ApiOperation(value = "条件查询角色")
    @GetMapping
    @RequiresPermissions("MENU_ADMIN_USER")
    public RestRes<List<PermissionMenuDTO>> findByRoleMenus(String search) {
        return permissionMenuService.findByRoleMenus(search);
    }

    /**
     * 修改菜单
     */
    @ApiOperation(value = "更新菜单")
    @PutMapping
    @RequiresPermissions("MENU_ADMIN_USER")
    public RestRes<Object> updateMenu(@RequestBody MenuDTO menuDTO) {
        return permissionMenuService.updateMenu(menuDTO);
    }

    /**
     * 删除菜单
     */
    @ApiOperation(value = "删除菜单")
    @DeleteMapping
    @RequiresPermissions("MENU_ADMIN_USER")
    public RestRes<Object> delete(@RequestParam String id) {
        return permissionMenuService.deleteById(id);
    }

    /**
     * 新增菜单
     */
    @ApiOperation(value = "新增菜单")
    @PostMapping
    @RequiresPermissions("MENU_ADMIN_USER")
    public RestRes<Object> creatMenu(@RequestBody MenuDTO menuDTO) {
        return permissionMenuService.addMenu(menuDTO);
    }


}
