package cn.gene.service.impl;

import cn.gene.pojo.User;
import cn.gene.mapper.UserMapper;
import cn.gene.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author  MX 
 * @since 2023-11-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
