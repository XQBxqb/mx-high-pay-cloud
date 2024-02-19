package cn.high.mx.module.manager.service;


import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.manager.dataobj.Role;
import cn.high.mx.module.manager.dto.ReqRole;
import cn.high.mx.module.manager.dto.ResRole;
import cn.high.mx.module.manager.dto.RoleDTO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface RoleService {

    public List<RoleDTO> getByName(String roleName);

    public RestRes<List<ResRole>> getAll();

    public RestRes<PageInfo<ResRole>> findBySearch(Integer page,Integer pageSize,String search);

    public RestRes<Object> update(ReqRole roleReq);

    public RestRes<Object> deletes(String ids);

    public RestRes<Object> addRole(ReqRole reqRole);
}
