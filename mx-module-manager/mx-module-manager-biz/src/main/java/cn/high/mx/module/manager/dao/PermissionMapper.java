package cn.high.mx.module.manager.dao;


import cn.high.mx.module.manager.dataobj.Permission;
import cn.high.mx.module.manager.dto.PermissionDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author  MX 
 * @since 2023-11-26
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    @Select("<script> select * " +
            "from permission " +
            "where id IN " +
            "<foreach item='item' collection = 'idList' open = '(' separator = ',' close = ')'> " +
            "#{item} " +
            "</foreach></script> ")
    public List<Permission> queryPermissionListByIdList(@Param("idList") List<Integer> ids);
    @Select("select * from permission ")
    public List<Permission> selectAll();
    @Select("select * from permission " +
            "where permission Like CONCAT('%',#{name},'%') OR permission LIKE CONCAT('%',#{name},'%') ")
    public List<Permission> findByLike(@Param("name") String name);
    @Delete("<script> delete from permission " +
            "where id in <foreach collection = 'ids' item = 'item' separator = ',' open = '(' close = ')' > " +
            "#{item} " +
            "</foreach></script> ")
    public void deleteList(@Param("ids") List<Integer> ids);

    public PermissionDTO selectDTOByRoleId(@Param("roleID") Integer roleId);

    @Select("select * " +
            "from permission " +
            "where id in( " +
            "select permission_id " +
            "from user_role_permission " +
            "where user_id = #{userId}) ")
    @ResultMap("permissionDTO")
    public List<PermissionDTO> selectDTOSById(@Param("userId") String userId);
}
