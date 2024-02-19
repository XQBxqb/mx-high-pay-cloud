package cn.high.mx.module.manager.dao;



import cn.high.mx.module.manager.dataobj.AdminUsers;
import cn.high.mx.module.manager.dto.AdminDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.tomcat.Jar;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author  MX 
 * @since 2023-11-26
 */
@Mapper
public interface AdminUsersMapper extends BaseMapper<AdminUsers> {
    @Select("select * from admin_users " +
            "where username = #{username} ")
   public AdminUsers selectByName(String username);
    @Select("select * " +
            "from admin_users " +
            "where username like concat('%',#{search},'%') or name like concat('%',#{search},'%') or " +
            "phone like concat('%',#{search},'%')  "
    )
    @ResultMap("adminDTO")
    public List<AdminDTO> selectDTOCondition(@Param("search") String search);

    @Select("select * " +
            "from admin_users ")
    @ResultMap("adminDTO")
    public List<AdminDTO> selectDTO();

    @Update("update admin_users set is_ban = 1 - is_ban where id = #{id} ")
    public void updateBan(@Param("id") Integer id);

    @Select("select id from admin_users " +
            "where username = #{username} ")
    public Integer getAdminId(@Param("username") String username);

    @Update("update admin_users set role = #{role} where role = #{oldRole} ")
    public void updateRole(@Param("role") String role,@Param("oldRole") String oldRole);
}
