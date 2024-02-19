package cn.high.mx.module.uaa.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.high.mx.module.uaa.dataobj.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * 客户表 Mapper 接口
 * </p>
 *
 * @author  MX 
 * @since 2023-11-28
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("update user " +
            "set last_login_time = CURRENT_TIMESTAMP() " +
            "where id = #{id} ")
    public void loginSyncInfo(@Param("id") Long id);
    @Update("update user " +
            "set password = #{password} " +
            "where id = #{id} ")
    public void updatePassword(@Param("id") Long id,@Param("password") String password);

}
