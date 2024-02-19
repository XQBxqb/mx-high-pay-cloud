package cn.high.mx.module.manager.controller.admin;

import cn.high.mx.framework.common.exception.enums.BizStatusEnum;
import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.framework.common.util.json.JsonUtils;
import cn.high.mx.framework.redis.config.RedissonService;
import cn.high.mx.module.manager.dto.*;
import cn.high.mx.module.manager.framework.redis.consts.RedisConst;
import cn.high.mx.module.manager.framework.shiro.core.base.BaseAdminUser;
import cn.high.mx.module.manager.service.AdminUserService;
import cn.high.mx.module.manager.service.PermissionService;
import cn.high.mx.module.manager.service.RolePermissionService;
import cn.high.mx.module.manager.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;


@Api(tags = "会话管理")
@RestController
@Validated
@RequiredArgsConstructor
public class SessionController {
    private final AdminUserService adminUserService;
    private final RedissonService redissonService;
    private final PermissionService permissionService;
    private final RoleService roleService;
    private final RolePermissionService rolePermissionService;



    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public RestRes<String> login(@RequestBody LoginDTO loginDTO) {
        return adminUserService.login(loginDTO.getUsername(), loginDTO.getPassword());
    }

    @ApiOperation(value = "查询用户是否存在")
    @GetMapping("/session/findByUsername")
    public RestRes<String> findByUsername(@NotBlank(message = "用户名必须存在") String username) {
        AdminUserDTO userDTO = adminUserService.getByName(username);
        if (userDTO == null)
            return RestRes.errorEnum(BizStatusEnum.RES_USER_NOT_EXIST);
        return RestRes.ok();
    }

    @ApiOperation(value = "获取用户会话信息")
    @GetMapping("/session")
    @RequiresPermissions("INDEX_ADMIN_USER")
    public RestRes<SessionDTO> session() {
        Subject subject = SecurityUtils.getSubject();
        BaseAdminUser adminUser = (BaseAdminUser) subject.getPrincipal();
        String role = adminUser.getRole();
        List<RoleDTO> roleDTOS = roleService.getByName(role);
        List<Integer> permissonIds = roleDTOS.stream()
                                             .flatMap(t -> rolePermissionService.getByRoleId(t.getId())
                                                                                .stream())
                                             .map(t -> Integer.valueOf(t))
                                             .collect(Collectors.toList());
        List<PermissionDTO> permissionDTOS = permissionService.getListByIds(permissonIds);
        return RestRes.ok(buildSessonDTO(adminUser, roleDTOS, permissionDTOS));
    }

    private SessionDTO buildSessonDTO(BaseAdminUser adminUser, List<RoleDTO> roleDTOList, List<PermissionDTO> permissionDTOS) {
        List<SessionDTO.RoleDTO> roleDTOS = roleDTOList
                .stream()
                .map(t -> {
                    SessionDTO.RoleDTO roleDTO = new SessionDTO.RoleDTO(t.getId(), t.getRole(), t.getRoleName());
                    return roleDTO;
                })
                .collect(Collectors.toList());
        List<SessionDTO.Authority> authoritiesDTOS = permissionDTOS
                .stream()
                .map(t -> {
                    SessionDTO.Authority authority = new SessionDTO.Authority(t.getPermission());
                    return authority;
                })
                .collect(Collectors.toList());
        List<PermissionDTO> permissonDTOS = permissionDTOS
                .stream()
                .map(t -> {
                    PermissionDTO permissionDTO = new PermissionDTO(t.getId(), t.getPermission(), t.getPermissionName());
                    return permissionDTO;
                })
                .collect(Collectors.toList());
        return SessionDTO.builder()
                         .username(adminUser.getUsername())
                         .id(adminUser.getId())
                         .isBan(adminUser.getIsBan())
                         .phone(adminUser.getPhone())
                         .role(adminUser.getRole())
                         .avatar(adminUser.getAvatar())
                         .name(adminUser.getName())
                         .roles(roleDTOS)
                         .authorities(authoritiesDTOS)
                         .permissions(permissonDTOS)
                         .build();
    }
}
