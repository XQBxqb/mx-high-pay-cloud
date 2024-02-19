package cn.high.mx.framework.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties("mx.shiro")
public class ShiroProperties {
    private String path;
}
