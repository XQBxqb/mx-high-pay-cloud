package cn.high.mx.module.uaa.service.impl;

import cn.high.mx.framework.common.exception.enums.BizStatusEnum;
import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.framework.common.util.json.JsonUtils;
import cn.high.mx.framework.redis.config.RedissonService;
import cn.high.mx.module.uaa.dao.UserMapper;
import cn.high.mx.module.uaa.dataobj.redis.UserCache;
import cn.high.mx.module.uaa.dto.ReqLoginDto;
import cn.high.mx.module.uaa.dto.ReqRegisterDto;
import cn.high.mx.module.uaa.dto.ReqUpdateDto;
import cn.high.mx.module.uaa.dto.ResUserCommonDto;
import cn.high.mx.module.uaa.framework.redis.consts.RedisConst;
import cn.high.mx.module.uaa.service.BaseService;
import cn.high.mx.module.uaa.service.UaaService;
import cn.high.mx.framework.common.util.dataEncrypt.DataEncryption;
import cn.high.mx.module.uaa.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import cn.high.mx.module.uaa.dataobj.User;



import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UaaServiceImpl extends BaseService implements UaaService {
    private final RedissonService redissonService;
    private final UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<String> login(ReqLoginDto reqLoginDto) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("phone",reqLoginDto.getMobile());
        userQueryWrapper.eq("password",reqLoginDto.getPassword());
        User user = userMapper.selectOne(userQueryWrapper);
        if(user==null){
            return RestRes.errorEnum(BizStatusEnum.RES_PASSWORD_INCORRECT);
        }
        userMapper.loginSyncInfo(user.getId());
        String token = JwtUtils.generateToken(user.getId(), JwtUtils.JwtConsts.JWT_TOKEN_KEY);
        redissonService.set(RedisConst.PERFIX_TOKEN+":"+user.getId(),token,RedisConst.DEFAULT_EXPIRE_TIME);
        redissonService.set(RedisConst.PERFIX_USER_INFO+":"+user.getId(),null,RedisConst.DEFAULT_EXPIRE_TIME);
        return RestRes.ok(token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<ResUserCommonDto> register(ReqRegisterDto reqRegisterDto) {
        User user = new User();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("phone",reqRegisterDto.getRegisterMobile());
        if(userMapper.selectOne(userQueryWrapper)!=null){
            return resCommonDto(BizStatusEnum.RES_REGISTER_PHONE_EXISTED);
        }
        user.setPhone(reqRegisterDto.getRegisterMobile());
        user.setPassword(reqRegisterDto.getRegisterPassword());
        userMapper.insert(user);
        return resCommonDto(BizStatusEnum.RES_SUCCESS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestRes<ResUserCommonDto> updatePass(ReqUpdateDto reqUpdateDto, HttpServletRequest request) {
        Long uid = parseToken(request);
        if(uid == null){
            return resCommonDto(BizStatusEnum.RES_TOKEN_ERROR);
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id",uid);
        userQueryWrapper.eq("password",reqUpdateDto.getOldPassword());
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null){
            return resCommonDto(BizStatusEnum.RES_PASSWORD_INCORRECT);
        }
        userMapper.updatePassword(uid,reqUpdateDto.getNewPassword());
        redissonService.delete(RedisConst.PERFIX_TOKEN+":"+uid);
        return resCommonDto(BizStatusEnum.RES_SUCCESS);
    }

    @Override
    public RestRes<ResUserCommonDto> logout(HttpServletRequest request) {
        Long uid = parseToken(request);
        if(uid == null){
            return resCommonDto(BizStatusEnum.RES_TOKEN_ERROR);
        }
        redissonService.delete(RedisConst.PERFIX_TOKEN+":"+uid);
        redissonService.delete(RedisConst.PERFIX_USER_INFO+":"+uid);
        return resCommonDto((BizStatusEnum.RES_SUCCESS));
    }
}
