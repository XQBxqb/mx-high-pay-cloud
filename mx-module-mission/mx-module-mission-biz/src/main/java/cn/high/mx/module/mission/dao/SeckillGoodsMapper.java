package cn.high.mx.module.mission.dao;


import cn.high.mx.module.mission.api.dto.GoodsDTO;
import cn.high.mx.module.mission.dataobj.SeckillGoods;
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
public interface SeckillGoodsMapper extends BaseMapper<SeckillGoods> {
    @Update("update seckill_goods " +
            "set stock_count = #{goodsDto.goodsStock} " +
            "where goods_id = #{goodsDto.id} ")
    public void updateGoods(@Param("goodsDto")GoodsDTO goodsDTO);
    @Select("select * " +
            "from seckill_goods ")
    public List<SeckillGoods> selectAll();
    @Select("select count(1) " +
            "from seckill_goods ")
    public int counts();
}
