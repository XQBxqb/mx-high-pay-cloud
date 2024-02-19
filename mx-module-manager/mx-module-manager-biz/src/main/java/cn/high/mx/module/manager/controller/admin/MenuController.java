package cn.high.mx.module.manager.controller.admin;

import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.manager.dataobj.PermissionMenu;
import cn.high.mx.module.manager.framework.shiro.core.consts.PermissionConst;
import cn.high.mx.module.manager.dto.PermissionMenuDTO;
import cn.high.mx.module.manager.service.PermissionMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "菜单管理")
@RestController
@RequestMapping("menu")
@RequiredArgsConstructor
public class MenuController {
    private final PermissionMenuService permissionMenuService;

    @ApiOperation(value = "获取所有菜单")
    @GetMapping
    @RequiresPermissions(PermissionConst.INDEX_ADMIN_USER)
    public RestRes<List<PermissionMenuDTO>> getMenu() {
        return permissionMenuService.getMenu();
    }
}
