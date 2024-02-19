package cn.gene.service.impl;

import cn.gene.pojo.DbVersion;
import cn.gene.mapper.DbVersionMapper;
import cn.gene.service.IDbVersionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author  MX 
 * @since 2023-11-28
 */
@Service
public class DbVersionServiceImpl extends ServiceImpl<DbVersionMapper, DbVersion> implements IDbVersionService {

}
