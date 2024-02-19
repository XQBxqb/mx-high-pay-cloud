package cn.high.mx.framework.security.config;

import cn.high.mx.framework.security.core.ShiroConfiguration;
import cn.high.mx.framework.security.core.jwt.JwtRealm;
import cn.high.mx.framework.security.core.ShiroFilter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableConfigurationProperties(ShiroProperties.class)
public class JwtAutoConfiguration {
    private final ShiroFilter shiroFilter;
    private final JwtRealm jwtRealm;

    @Autowired
    @SneakyThrows
    public JwtAutoConfiguration(ShiroProperties shiroProperties) {
        Class<?> configurationClass = Class.forName(shiroProperties.getPath());
        Object instance = configurationClass.newInstance();
        if(!ShiroConfiguration.class.isAssignableFrom(configurationClass))
            throw new RuntimeException();
        ShiroConfiguration shiroConfiguration = (ShiroConfiguration) instance;
        ShiroFilter shiroFilter = shiroConfiguration.getShiroFilter();
        JwtRealm jwtRealm = shiroConfiguration.getJwtRealm();
        this.shiroFilter = shiroFilter;
        this.jwtRealm = jwtRealm;
    }

    public ShiroFilter getShiroRouting() {
        return shiroFilter;
    }

    public JwtRealm getJwtRealm() {
        return jwtRealm;
    }
}