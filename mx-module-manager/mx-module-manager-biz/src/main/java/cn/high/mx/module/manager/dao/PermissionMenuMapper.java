package cn.high.mx.module.manager.dao;



import cn.high.mx.module.manager.dataobj.PermissionMenu;
import cn.high.mx.module.manager.dto.PermissionMenuDTO;
import cn.high.mx.module.manager.dto.TreeRoleMenuDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 权限菜单表 Mapper 接口
 * </p>
 *
 * @author  MX 
 * @since 2023-11-26
 */
@Mapper
public interface PermissionMenuMapper extends BaseMapper<PermissionMenu> {
    @Select("<script>select * from permission_menu " +
            "where permission_id in " +
            "<foreach item = 'item' collection = 'permissionIds' open = '(' close = ')' separator = ',' > " +
            "#{item} " +
            "</foreach> " +
            "ORDER BY id ASC " +
            "</script> ")
    public List<PermissionMenu> selectByPermissionIds(@Param("permissionIds") List<Integer> permissionIds);

    @Select("select * from permission_menu ")
    @ResultMap("treeRoleMenuDto")
    public List<TreeRoleMenuDTO> selectMenuList();
    @Select("select * from permission_menu " +
            "where key concat('%',#{search},'%') or name concat('%',#{search},'%') or icon concat('%',#{search},'%') ")
    public List<PermissionMenu> findByLike(@Param("search") String search);
    @Select("select * from permission_menu ")
    public List<PermissionMenu> selectAll();
}
