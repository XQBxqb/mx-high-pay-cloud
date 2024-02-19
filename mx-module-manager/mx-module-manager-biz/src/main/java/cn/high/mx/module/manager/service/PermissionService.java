package cn.high.mx.module.manager.service;



import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.manager.dto.PermissionDTO;
import cn.high.mx.module.manager.dto.ReqPermission;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PermissionService {
    public List<PermissionDTO> getListByIds(List<Integer> ids);

    public RestRes<List<PermissionDTO>> findAll();

    public RestRes<PageInfo<PermissionDTO>> findByName(Integer page, Integer pageSize,String name);

    public RestRes<Object> deletes(String ids);

    public RestRes<Object> addPermission(ReqPermission reqPermission);

    public RestRes<Object> updatePermission(ReqPermission reqPermission);

}
