package cn.high.mx.module.manager.framework.shiro.config;


import cn.high.mx.framework.common.util.json.JsonUtils;
import cn.high.mx.module.manager.framework.shiro.core.consts.JwtConsts;
import cn.high.mx.module.manager.framework.shiro.core.jwt.JwtToken;
import cn.high.mx.module.manager.utils.local.ThreadLocalData;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;



import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        log.info("isAccessAllowed : 验证是否拥有token");
        String token = ((HttpServletRequest) request).getHeader(JwtConsts.JWT_TOKEN_HEAD);

        HttpServletResponse servletResponse = (HttpServletResponse) response;
        if (StringUtils.isBlank(token)) {
            try {
                setReturnInfo(servletResponse, 401, "token为空");
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return false;
        }
        return executeLogin(request, response);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        log.info("executeLogin : 执行登录");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = httpServletRequest.getHeader(JwtConsts.JWT_TOKEN_HEAD);
        ThreadLocalData.setThreadLocal(new Pair<>("token",token));
        JwtToken jwtToken = new JwtToken(token.substring(7));
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        try {
            getSubject(request, response).login(jwtToken);
        } catch (Exception e) {
            log.info("认证出现异常：{}", e.getMessage());
            ThreadLocalData.destroy();
            try {
                setReturnInfo(httpServletResponse, 401, "token错误");
            } catch (IOException ex) {
            }
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.info("登录失败");
        ThreadLocalData.destroy();
        return super.onAccessDenied(request, response);
    }

    private static void setReturnInfo(HttpServletResponse response, int status, String msg) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        Map<String, String> result = new HashMap<>();
        result.put("status", String.valueOf(status));
        result.put("msg", msg);
        response.getWriter()
                .write(JsonUtils.toJsonString(result));
    }
}