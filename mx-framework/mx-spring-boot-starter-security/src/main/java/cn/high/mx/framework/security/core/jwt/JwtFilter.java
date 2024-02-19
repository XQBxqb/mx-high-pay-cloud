package cn.high.mx.framework.security.core.jwt;

import cn.high.mx.framework.common.exception.BizException;
import cn.high.mx.framework.common.exception.enums.BizStatusEnum;
import cn.high.mx.framework.security.core.consts.JwtConsts;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 昴星
 * @date 2023-10-06 20:20
 * @explain
 */
@Slf4j
public class JwtFilter extends AccessControlFilter {
    /*
     * 1. 返回true，shiro就直接允许访问url
     * 2. 返回false，shiro才会根据onAccessDenied的方法的返回值决定是否允许访问url
     * */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //这里先让它始终返回false来使用onAccessDenied()方法
        return false;
    }

    /**
     * 返回结果为true表明登录通过
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) {
        log.warn("onAccessDenied 方法被调用");
        //所以以后发起请求的时候就需要在Header中放一个Authorization，值就是对应的Token
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader(JwtConsts.JWT_TOKEN_HEAD);
        log.info("请求的 Header 中藏有 jwtToken {}", jwt);
        JwtToken jwtToken = new JwtToken(jwt);
        try {
            // 委托 realm 进行登录认证
            //所以这个地方最终还是调用JwtRealm进行的认证
            getSubject(request, servletResponse).login(jwtToken);
            //也就是subject.login(token)
        } catch (Exception e) {
            log.error("error request token exception: "+e.getClass().getName()+" msg: "+e.getMessage(),e);
            throw new BizException(BizStatusEnum.RES_TOKEN_ERROR);
        }
        return true;
    }
}


