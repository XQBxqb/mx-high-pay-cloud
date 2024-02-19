package cn.high.mx.module.mission.service;


import cn.high.mx.module.mission.exception.BizException;
import cn.high.mx.module.mission.exception.enums.BizStatusEnum;
import cn.high.mx.module.mission.utils.JwtConsts;
import cn.high.mx.module.mission.utils.JwtUtils;
import cn.high.mx.module.mission.utils.SM3Util;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

@NoArgsConstructor
public class BaseService {

    public static void copy(Object dest, Object orig) {
        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public long parseToken(HttpServletRequest request){
        String token = request.getHeader(JwtConsts.JWT_TOKEN_HEAD).substring(7);
        if(StringUtils.isBlank(token)){
            throw new BizException(BizStatusEnum.RES_TOKEN_BLANK);
        }
        Long uid = Long.parseLong(JwtUtils.parseToken(token, JwtConsts.JWT_TOKEN_KEY));
        return uid;
    }

    public String generatorPath(Long uid,Long goodId){
        if(uid == null||goodId == null){
            throw new BizException(BizStatusEnum.RES_PATH_GENERATOR_ERROR);
        }
        return SM3Util.sm3(String.valueOf(uid) + "-" + String.valueOf(goodId));
    }

    public Long generatorOrderId(Long uid,Long goodId){
        return goodId*1000000+uid;
    }

    public static void main(String[] args) {
        String token = JwtUtils.generateToken("3", JwtConsts.JWT_TOKEN_KEY);
        System.out.println(token);
    }
}
