package cn.high.mx.framework.mq.config;


import cn.high.mx.framework.common.util.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableConfigurationProperties(RabbitmqProperties.class)
@Slf4j
public class CachingConnectionFactoryAutoConfiguration {
        @Bean
        @Primary
        public CachingConnectionFactory connectionFactory(RabbitmqProperties rabbitmqProperties) {
            CachingConnectionFactory  connectionFactory = new CachingConnectionFactory ();
            connectionFactory.setHost(rabbitmqProperties.getHost());
            connectionFactory.setPort(Integer.parseInt(rabbitmqProperties.getPort()));
            connectionFactory.setUsername(rabbitmqProperties.getUsername());
            connectionFactory.setPassword(rabbitmqProperties.getPassword());
            log.info("init Rabbitmq Connection finish , params {}", JsonUtils.toJsonString(rabbitmqProperties));
            return connectionFactory;
        }
}
