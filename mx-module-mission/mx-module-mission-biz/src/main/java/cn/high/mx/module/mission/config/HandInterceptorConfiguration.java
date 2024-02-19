package cn.high.mx.module.mission.config;

import cn.high.mx.module.mission.interceptor.AuthenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HandInterceptorConfiguration implements WebMvcConfigurer {
    @Autowired
    private AuthenInterceptor authenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/actuator/**","/swagger-ui/**","/swagger-resources/**","/v3/**","/error");
    }
}
