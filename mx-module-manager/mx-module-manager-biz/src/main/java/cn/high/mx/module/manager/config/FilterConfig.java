package cn.high.mx.module.manager.config;

import cn.high.mx.framework.log.core.filter.CommonLogerFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<CommonLogerFilter> loggingFilter() {
        FilterRegistrationBean<CommonLogerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CommonLogerFilter());
        registrationBean.addUrlPatterns("/*"); // 这里设置了过滤的 URL 模式，可以根据需要进行调整
        return registrationBean;
    }
}
