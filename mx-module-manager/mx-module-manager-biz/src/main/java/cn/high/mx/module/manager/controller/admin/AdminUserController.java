package cn.high.mx.module.manager.controller.admin;


import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.manager.dto.AdminDTO;
import cn.high.mx.module.manager.dto.ReqAdminUser;
import cn.high.mx.module.manager.dto.ReqPatchAdminUser;
import cn.high.mx.module.manager.dto.ReqRegisterUserDTO;
import cn.high.mx.module.manager.service.AdminUserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "管理员用户管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("adminUser")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @ApiOperation("获取管理员列表")
    @GetMapping
    @RequiresPermissions("ROLE_ADMIN_USER")
    public RestRes<PageInfo<AdminDTO>> getDTOs(Integer page,Integer pageSize,String search){
        return adminUserService.conditionAdmins(page,pageSize,search);
    }
    @ApiOperation("更新管理员")
    @PutMapping
    @RequiresPermissions("ROLE_ADMIN_USER")
    public RestRes<Object> updateAdminUserInfo(@RequestBody @Valid ReqAdminUser reqAdminUser){
        return adminUserService.update(reqAdminUser);
    }
    @ApiOperation("部分更新管理员")
    @PatchMapping
    @RequiresPermissions("ROLE_ADMIN_USER")
    public RestRes<Object> patchUpdateInfo(@RequestBody @Valid ReqPatchAdminUser reqPatchAdminUser){
        return adminUserService.patchUpdate(reqPatchAdminUser);
    }

    @ApiOperation("删除管理员")
    @DeleteMapping
    @RequiresPermissions("ROLE_ADMIN_USER")
    public RestRes<Object> deletesAdmin(@RequestParam String ids){
        return adminUserService.deletesAdmin(ids);
    }

    @ApiOperation("添加管理员")
    @PostMapping
    @RequiresPermissions("ROLE_ADMIN_USER")
    public RestRes<Object> addAdminUser(@RequestBody @Valid ReqRegisterUserDTO reqRegisterUserDTO){
        return adminUserService.addAdmin(reqRegisterUserDTO);
    }

    @ApiOperation("变更管理员启用状态")
    @PatchMapping("/{id}")
    @RequiresPermissions("ROLE_ADMIN_USER")
    public RestRes<Object> updateBan(@PathVariable("id") Integer id){
        return adminUserService.updateBan(id);
    }
}
