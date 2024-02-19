package cn.high.mx.module.uaa.interceptor;

import cn.high.mx.framework.redis.config.RedissonService;
import cn.high.mx.module.uaa.utils.JwtUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuthenInterceptor implements HandlerInterceptor {
    private final RedissonService redissonService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("正在认证...");
        String token = request.getHeader(JwtUtils.JwtConsts.JWT_TOKEN_HEAD).substring(7);
        Long uid = JwtUtils.parseToken(token, JwtUtils.JwtConsts.JWT_TOKEN_KEY);
        if(uid == null){
            log.info("认证失败...");
            return false;
        }
        log.info("认证成功!");
        return true;
    }
}
