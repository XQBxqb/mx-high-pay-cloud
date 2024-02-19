package cn.gene.service.impl;

import cn.gene.pojo.Goods;
import cn.gene.mapper.GoodsMapper;
import cn.gene.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author  MX 
 * @since 2023-12-03
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

}
