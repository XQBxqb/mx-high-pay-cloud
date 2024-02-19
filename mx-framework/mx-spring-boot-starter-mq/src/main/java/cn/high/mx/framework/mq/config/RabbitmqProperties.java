package cn.high.mx.framework.mq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties("mx.rabbitmq")
public class RabbitmqProperties {
    private String host;

    private String port;

    private String username;

    private String password;

}
