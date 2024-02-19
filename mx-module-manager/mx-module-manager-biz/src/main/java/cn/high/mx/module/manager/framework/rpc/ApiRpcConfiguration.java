package cn.high.mx.module.manager.framework.rpc;

import cn.high.mx.module.manager.framework.shiro.core.consts.JwtConsts;
import cn.high.mx.module.manager.utils.local.ThreadLocalData;
import cn.high.mx.module.mission.api.*;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.cloud.openfeign.EnableFeignClients;
import cn.high.mx.module.uaa.api.UserApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {UserApi.class, FileApi.class, GoodApi.class, HomeApi.class, OrderApi.class, SeckillApi.class})
public class ApiRpcConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new CustomRequestInterceptor();
    }

    private class CustomRequestInterceptor implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate template) {
            template.header(JwtConsts.JWT_TOKEN_HEAD, ThreadLocalData.getThreadLocal("token"));
        }
    }
}
