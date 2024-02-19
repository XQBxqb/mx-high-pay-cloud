package cn.high.mx.module.manager.controller.admin;

import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.manager.dto.ReqRole;
import cn.high.mx.module.manager.dto.ResRole;

import cn.high.mx.module.manager.service.RoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "角色管理")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @ApiOperation(value = "查询所有角色")
    @GetMapping("/findAll")
    public RestRes<List<ResRole>> findAll() {
        return roleService.getAll();
    }

    /**
     * 角色列表
     */
    @ApiOperation(value = "分页条件查询角色")
    @GetMapping
    public RestRes<PageInfo<ResRole>> findByRoles(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                  @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
                                                  String search) {
        return roleService.findBySearch(page, pageSize, search);
    }

    /**
     * 修改角色
     */
    @ApiOperation(value = "更新角色")
    @PutMapping
    public RestRes<Object> updateRole(@RequestBody ReqRole roleReq) {
        return roleService.update(roleReq);
    }


    /**
     * 删除角色
     */
    @ApiOperation(value = "")
    @DeleteMapping
    public RestRes<Object> deletes(@RequestParam String ids) {
        return roleService.deletes(ids);
    }

    /**
     * 新增角色
     */
    @ApiOperation(value = "")
    @PostMapping
    public RestRes<Object> createRole(@RequestBody ReqRole roleReq) {
        return roleService.addRole(roleReq);
    }
}
