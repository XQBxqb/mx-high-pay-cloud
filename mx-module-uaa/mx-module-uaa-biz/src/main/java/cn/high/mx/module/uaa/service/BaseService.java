package cn.high.mx.module.uaa.service;

import cn.high.mx.framework.common.exception.enums.BaseStatusEnum;
import cn.high.mx.framework.common.exception.enums.BizStatusEnum;
import cn.high.mx.framework.common.res.RestRes;
import cn.high.mx.module.uaa.dto.ResUserCommonDto;
import cn.high.mx.module.uaa.utils.JwtUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

public class BaseService {
    public RestRes<ResUserCommonDto> resCommonDto(BaseStatusEnum baseStatusEnum){
        return RestRes.errors(baseStatusEnum.getMessage(),baseStatusEnum.getCode(), new ResUserCommonDto(baseStatusEnum),baseStatusEnum.getSuccess());
    }

    public void copy(Object dest, Object orig){
        try {
            BeanUtils.copyProperties(dest,orig);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Long parseToken(HttpServletRequest request){
        String token = request.getHeader(JwtUtils.JwtConsts.JWT_TOKEN_HEAD).substring(7);
        Long uid = JwtUtils.parseToken(token, JwtUtils.JwtConsts.JWT_TOKEN_KEY);
        return uid;
    }
}
