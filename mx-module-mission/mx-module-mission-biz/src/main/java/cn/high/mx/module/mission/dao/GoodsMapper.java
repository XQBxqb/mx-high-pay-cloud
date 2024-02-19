package cn.high.mx.module.mission.dao;


import cn.high.mx.module.mission.dataobj.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 *
 * @author  MX 
 * @since 2023-12-03
 */

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    @Select("select * " +
            "from goods " +
            "where goods_name LIKE CONCAT('%',#{name},'%') ")
    public List<Goods> findByNameLike(@Param("name") String name);
    @Select("select * " +
            "from goods ")
    public List<Goods> selectAll();
    @Update("update goods " +
            "set is_using = is_using ^ 1 " +
            "where id = #{id} ")
    public void updateUsing(@Param("id") Long id);
    @Select("select count(1) " +
            "from goods ")
    public int counts();
    @Select("<script>select goods_name " +
            "from goods " +
            "where id in " +
            "<foreach collection = 'ids' item = 'item' separator = ',' open = '(' close = ')' > " +
            "#{item} " +
            "</foreach>" +
            "ORDER BY id ASC " +
            "</script> ")
    public List<String> selectByIdsSortById(@Param("ids") List<Long> ids);
}
