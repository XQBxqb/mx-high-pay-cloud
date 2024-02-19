package cn.high.mx.framework.log.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("mx.logstash")
@ConditionalOnProperty("mx.logstash.address")
public class LogProperties {
    private String address;

    private String name;
}
