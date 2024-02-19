package cn.high.mx.module.manager.dao;



import cn.high.mx.module.manager.dataobj.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 角色权限表 Mapper 接口
 * </p>
 *
 * @author  MX 
 * @since 2023-11-26
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    @Select("select permission_id " +
            "from role_permission " +
            "where role_id = #{roleId} ")
    public List<String> selectPermissionIdList(@Param("roleId") Integer roleId);
    @Delete("<script>delete from role_permission " +
            "where role_id = #{roleId} and permission_id not in " +
            "<foreach collection = 'permissionList' item = 'item' separator = ',' open = '(' close = ')'> " +
            "#{item} " +
            "</foreach></script>" )
    public void deleteNotInPermissionIds(@Param("roleId") Integer roleId,@Param("permissionList") List<String> permissionList);
    @Insert("<script>insert into role_permission(role_id,permission_id) values " +
            "<foreach collection = 'permissionList' separator =','  item = 'item'> " +
            "(#{roleId},#{item}) </foreach></script> ")
    public void batchInsert(@Param("roleId") Integer roleId,@Param("permissionList") List<String> permissionList);
    @Select("select * from role_permission " +
            "where role_id = #{roleId} ")
    public List<RolePermission> selectByRoleId(@Param("roleId") Integer roleId);
    @Delete("<script> delete from role_permission where role_id in <foreach collection = 'ids' separator = ',' open = '(' close = ')' item = 'item'> #{item}</foreach></script> ")
    public void deletes(@Param("ids") List<Integer> ids);
    @Insert("<script> insert into role_permission (role_id,permission_id) values " +
            "<foreach collection = 'ids' item = 'item' separator = ',' >  " +
            "(#{roleId},#{item}) </foreach></script> ")
    public void insertList(@Param("roleId") Integer roleId,@Param("ids") List<String> permissionIds);
}
