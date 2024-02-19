package cn.high.mx.module.manager.service;


import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.manager.dto.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminUserService {
    public AdminUserDTO getById(Integer id);

    public AdminUserDTO getByName(String name);

    public RestRes<String> login(String username, String password);

    public RestRes<PageInfo<AdminDTO>> conditionAdmins(Integer page, Integer pageSize, String search);

    public RestRes<Object> update(ReqAdminUser reqAdminUser);

    public RestRes<Object> patchUpdate(ReqPatchAdminUser reqPatchAdminUser);

    public RestRes<Object> deletesAdmin(String ids);

    public RestRes<Object> addAdmin(ReqRegisterUserDTO reqRegisterUserDTO);

    public RestRes<Object> updateBan(Integer id);
}
