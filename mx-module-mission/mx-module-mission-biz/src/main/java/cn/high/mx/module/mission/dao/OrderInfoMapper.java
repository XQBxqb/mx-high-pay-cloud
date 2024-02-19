package cn.high.mx.module.mission.dao;


import cn.high.mx.module.mission.dataobj.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * @author  MX 
 * @since 2023-12-03
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    @Select("select * " +
            "from order_info ")
    public List<OrderInfo> selectAll();
    @Select("select count(*) " +
            "from order_info ")
    public int counts();
}
