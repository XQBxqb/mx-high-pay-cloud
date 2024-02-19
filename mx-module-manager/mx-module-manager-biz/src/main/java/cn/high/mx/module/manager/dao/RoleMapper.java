package cn.high.mx.module.manager.dao;



import cn.high.mx.module.manager.dataobj.Permission;
import cn.high.mx.module.manager.dataobj.Role;
import cn.high.mx.module.manager.dto.ResRole;
import cn.high.mx.module.manager.dto.RoleDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author  MX 
 * @since 2023-11-26
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select * from role ")
    public List<Role> selectAll();

    @Select("select * " +
            "from role " +
            "where role like concat ('%',#{search},'%') or role_name like concat ('%',#{search},'%')")
    public List<Role> selectBySearch(@Param("search") String search);

    @Select("SELECT * " +
            "FROM permission " +
            "LEFT JOIN ( " +
            "    SELECT CONVERT(rp.permission_id, SIGNED) AS permission_id_int " +
            "    FROM ( " +
            "        SELECT permission_id " +
            "        FROM role_permission " +
            "        WHERE role_id = #{roleId} " +
            "    ) AS rp " +
            ") AS temp " +
            "ON permission.id = temp.permission_id_int;")
    public List<Permission> selectPermissionsByRoleId(@Param("roleId") Integer roleId);

    @Select("select * from role ")
    @ResultMap("resRole")
    public List<ResRole> selectRoleDTOs();

    @Select("select * from role " +
            "where role like concat ('%',#{search},'%') or role_name like concat ('%',#{search},'%')")
    @ResultMap("resRole")
    public List<ResRole> selectRoleDTOsBySearch(@Param("search") String search);
    @Delete("<script> delete from role where id in <foreach collection = 'ids' item = 'item' separator = ',' open = '(' close = ')'> " +
            "#{item} </foreach></script> ")
    public void deletes(@Param("ids") List<Integer> ids);

    @Select("select * from role " +
            "where role = #{role} ")
    @ResultMap("roleDTO")
    public List<RoleDTO> selectDTOByName(@Param("role") String role);
    @Select("select role from role where id = #{id} ")
    public String selectOldRoleById(@Param("id") Integer id);
}
