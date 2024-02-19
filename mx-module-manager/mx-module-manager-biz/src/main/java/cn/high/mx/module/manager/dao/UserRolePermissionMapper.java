package cn.high.mx.module.manager.dao;




import cn.high.mx.module.manager.dataobj.UserRolePermission;
import cn.high.mx.module.manager.dto.ReqPatchAdminUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 用户角色权限表 Mapper 接口
 * </p>
 *
 * @author  MX 
 * @since 2023-11-26
 */
@Mapper
public interface UserRolePermissionMapper extends BaseMapper<UserRolePermission> {
    @Select("select count(*) " +
            "from user_role_permission " +
            "where role_id = #{roleId} ")
    public Integer selectCountHasRole(@Param("roleId") Integer roleId);
    @Select("select role_id " +
            "from user_role_permission " +
            "where user_id = #{userId} " +
            "limit 1 ")
    public Integer selectRoleIdByUserId(@Param("userId") Integer userId);

    @Insert("<script> insert into user_role_permission(user_id,role_id,permission_id) values " +
            "<foreach collection = 'req.permissionIds' item = 'item'  separator = ','> " +
            "(#{req.id},#{roleId},#{item}) </foreach></script> ")
    public void insertBatch(@Param("roleId") Integer roleId, @Param("req") ReqPatchAdminUser reqPatchAdminUser);
    @Delete("<script> delete from user_role_permission where user_id in " +
            "<foreach collection = 'ids' item = 'item' open = '(' close = ')' separator= ',' > " +
            "#{item} </foreach>  </script> ")
    public void deleteByUserIds(@Param("ids") List<Integer> ids);

    @Insert("<script>insert into user_role_permission(user_id,role_id,permission_id) values " +
            "<foreach collection = 'permissions' item = 'item'  separator = ','> " +
            "(#{userId},#{roleId},#{item}) </foreach></script> ")
    public void batchInsert(@Param("userId") Integer userId,@Param("roleId") Integer roleId,@Param("permissions") List<Integer> permissions);

}
