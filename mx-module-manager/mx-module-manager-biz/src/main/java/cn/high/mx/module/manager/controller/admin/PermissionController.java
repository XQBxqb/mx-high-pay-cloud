package cn.high.mx.module.manager.controller.admin;

import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.manager.dto.PermissionDTO;
import cn.high.mx.module.manager.dto.ReqPermission;
import cn.high.mx.module.manager.service.PermissionService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "权限管理")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("permission")
public class PermissionController {
    private final PermissionService permissionService;

    @ApiOperation(value = "查询到所有权限")
    @GetMapping("/findAll")
    @RequiresPermissions("PERMISSION_ADMIN_USER")
    public RestRes<List<PermissionDTO>> findAll() {
        return permissionService.findAll();
    }


    @ApiOperation(value = "分页条件查询权限")
    @GetMapping
    public RestRes<PageInfo<PermissionDTO>> findByPermissions(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                              @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
                                                              @RequestParam(required = false) String search) {
        return permissionService.findByName(page, pageSize, search);
    }

    @ApiOperation(value = "批量删除权限")
    @DeleteMapping
    @RequiresPermissions("PERMISSION_ADMIN_USER")
    public RestRes<Object> deletes(@RequestParam String ids) {
        return permissionService.deletes(ids);
    }

    @ApiOperation(value = "创建新的权限")
    @PostMapping
    @RequiresPermissions("PERMISSION_ADMIN_USER")
    public RestRes<Object> createPermission(@Valid @RequestBody ReqPermission reqPermission) {
        return permissionService.addPermission(reqPermission);
    }

    @ApiOperation(value = "更新权限")
    @PutMapping
    @RequiresPermissions("PERMISSION_ADMIN_USER")
    public RestRes<Object> update(@Valid @RequestBody ReqPermission reqPermission) {
        return permissionService.updatePermission(reqPermission);
    }

}
