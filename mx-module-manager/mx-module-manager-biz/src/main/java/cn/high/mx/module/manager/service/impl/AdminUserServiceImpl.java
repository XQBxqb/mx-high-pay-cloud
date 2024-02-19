package cn.high.mx.module.manager.service.impl;


import cn.high.mx.framework.common.exception.enums.BizStatusEnum;
import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.framework.common.util.dataEncrypt.DataEncryption;
import cn.high.mx.framework.common.util.json.JsonUtils;
import cn.high.mx.framework.redis.config.RedissonService;
import cn.high.mx.module.manager.dao.AdminUsersMapper;
import cn.high.mx.module.manager.dao.RoleMapper;
import cn.high.mx.module.manager.dao.RolePermissionMapper;
import cn.high.mx.module.manager.dao.UserRolePermissionMapper;
import cn.high.mx.module.manager.dataobj.AdminUsers;
import cn.high.mx.module.manager.dataobj.Role;
import cn.high.mx.module.manager.dataobj.UserRolePermission;
import cn.high.mx.module.manager.dto.*;
import cn.high.mx.module.manager.framework.redis.consts.RedisConst;
import cn.high.mx.module.manager.framework.shiro.core.consts.JwtConsts;
import cn.high.mx.module.manager.framework.shiro.core.util.JwtUtils;
import cn.high.mx.module.manager.service.AdminUserService;
import cn.high.mx.module.manager.service.BaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl extends BaseService implements AdminUserService  {

    private final AdminUsersMapper adminUsersMapper;

    private final RedissonService redissonService;

    private final RolePermissionMapper rolePermissionMapper;

    private final UserRolePermissionMapper userRolePermissionMapper;

    private final RoleMapper roleMapper;
    @Override
    public AdminUserDTO getById(Integer id) {
        AdminUsers adminUsers = adminUsersMapper.selectById(id);
        AdminUserDTO adminUserDto = new AdminUserDTO();
        copy(adminUserDto,adminUsers);
        return adminUserDto;
    }

    @Override
    public AdminUserDTO getByName(String name) {
        String jsonCache = redissonService.get(RedisConst.PREFIX_ADMIN_INFO + ":" + name, String.class);
        if(StringUtils.isNotBlank(jsonCache)){
            AdminUserDTO adminUserDto = JsonUtils.parseObject(jsonCache, AdminUserDTO.class);
            redissonService.expire(RedisConst.PREFIX_ADMIN_INFO + ":" + name,RedisConst.DEFAULT_EXPIRE_TIME);
            return adminUserDto;
        }
        AdminUsers adminUsers = adminUsersMapper.selectByName(name);
        if(adminUsers == null)
            return null;
        AdminUserDTO adminUserDto = getUserDto(name, adminUsers);
        return adminUserDto;
    }

    private AdminUserDTO getUserDto(String name, AdminUsers adminUsers) {
        AdminUserDTO adminUserDTO = new AdminUserDTO();
        copy(adminUserDTO,adminUsers);
        String cacheObj = JsonUtils.toJsonString(adminUserDTO);
        redissonService.set(RedisConst.PREFIX_ADMIN_INFO + ":" + name,cacheObj,RedisConst.DEFAULT_EXPIRE_TIME);
        return adminUserDTO;
    }

    @Override
    public RestRes<String> login(String username, String password) {
        AdminUsers adminUsers = adminUsersMapper.selectByName(username);
        AdminUserDTO adminUserDTO = new AdminUserDTO();
        copy(adminUserDTO,adminUsers);
        if(adminUsers == null || !DataEncryption.calculateSHA256(password).equals(adminUsers.getPassword()))
            return RestRes.errorEnum(BizStatusEnum.RES_PASSWORD_INCORRECT);
        String token = JwtUtils.generateToken( username, JwtConsts.JWT_TOKEN_KEY);
        updateCache(username, token, adminUserDTO);
        return RestRes.ok(token);
    }

    @Override
    public RestRes<PageInfo<AdminDTO>> conditionAdmins(Integer page, Integer pageSize, String search) {
        PageHelper.startPage(page,pageSize);
        List<AdminDTO> adminDTOList ;
        adminDTOList = StringUtils.isBlank(search) ? adminUsersMapper.selectDTO() : adminUsersMapper.selectDTOCondition(search);
        return RestRes.ok(new PageInfo<>(adminDTOList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Object> update(ReqAdminUser reqAdminUser) {
        AdminUsers adminUsers = new AdminUsers();
        copy(adminUsers,reqAdminUser);
        if(StringUtils.isNotBlank(adminUsers.getPassword()))
            adminUsers.setPassword(DataEncryption.calculateSHA256(adminUsers.getPassword()));
        adminUsersMapper.updateById(adminUsers);
        return RestRes.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Object> patchUpdate(ReqPatchAdminUser reqPatchAdminUser) {
        QueryWrapper<UserRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",reqPatchAdminUser.getId());
        Integer roleId = userRolePermissionMapper.selectRoleIdByUserId(reqPatchAdminUser.getId());
        userRolePermissionMapper.delete(queryWrapper);
        userRolePermissionMapper.insertBatch(roleId,reqPatchAdminUser);
        return RestRes.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Object> deletesAdmin(String ids) {
        List<Integer> userIds = Arrays.stream(ids.split(","))
                                      .map(Integer::valueOf).collect(Collectors.toList());
        if(userIds.size() == 0){
            return RestRes.errorEnum(BizStatusEnum.RES_IDS_BLANK);
        }
        adminUsersMapper.deleteBatchIds(userIds);
        userRolePermissionMapper.deleteByUserIds(userIds);
        return RestRes.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Object> addAdmin(ReqRegisterUserDTO reqRegisterUserDTO) {
        AdminUsers adminUsers = getAdminUsers(reqRegisterUserDTO);
        adminUsersMapper.insert(adminUsers);
        Integer adminId = adminUsersMapper.getAdminId(adminUsers.getUsername());
        List<Integer> permissions = rolePermissionMapper.selectPermissionIdList(reqRegisterUserDTO.getRoleId())
                                                 .stream()
                                                 .map(Integer::valueOf)
                                                 .collect(Collectors.toList());
        userRolePermissionMapper.batchInsert(adminId,reqRegisterUserDTO.getRoleId(),permissions);
        return RestRes.ok();
    }

    private AdminUsers getAdminUsers(ReqRegisterUserDTO reqRegisterUserDTO) {
        AdminUsers adminUsers = new AdminUsers();
        copy(adminUsers, reqRegisterUserDTO);
        Role role = roleMapper.selectById(reqRegisterUserDTO.getRoleId());
        adminUsers.setName(role.getRoleName());
        adminUsers.setRole(role.getRole());
        adminUsers.setUsername(reqRegisterUserDTO.getName());
        adminUsers.setPassword(DataEncryption.calculateSHA256(adminUsers.getPassword()));
        return adminUsers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<Object> updateBan(Integer id) {
        adminUsersMapper.updateBan(id);
        return RestRes.ok();
    }

    private void updateCache(String username, String token, AdminUserDTO adminUsers) {
        String jsonString = JsonUtils.toJsonString(adminUsers);
        redissonService.set(RedisConst.PREFIX_TOKEN + ":" + username, token,RedisConst.DEFAULT_EXPIRE_TIME);
        redissonService.set(RedisConst.PREFIX_ADMIN_INFO+":"+ username,jsonString,RedisConst.DEFAULT_EXPIRE_TIME);
    }
}
