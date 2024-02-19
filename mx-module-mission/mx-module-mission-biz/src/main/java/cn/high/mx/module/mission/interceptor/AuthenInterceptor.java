package cn.high.mx.module.mission.interceptor;

import cn.high.mx.module.mission.utils.JwtConsts;

import cn.high.mx.module.mission.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class AuthenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(JwtConsts.JWT_TOKEN_HEAD).substring(7);
        String data = JwtUtils.parseToken(token, JwtConsts.JWT_TOKEN_KEY);
        return StringUtils.isBlank(data) ? false : true;
    }
}
