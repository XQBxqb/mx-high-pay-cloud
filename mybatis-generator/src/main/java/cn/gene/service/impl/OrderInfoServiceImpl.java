package cn.gene.service.impl;

import cn.gene.pojo.OrderInfo;
import cn.gene.mapper.OrderInfoMapper;
import cn.gene.service.IOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author  MX 
 * @since 2023-12-03
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

}
