package cn.high.mx.framework.log.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.NoArgsConstructor;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@NoArgsConstructor
@EnableConfigurationProperties(LogProperties.class)
@ConditionalOnProperty(value = "mx.logstash.enable",havingValue = "true")
public class LogAutoConfiguration {
    private static final String LOGSTASH_APPENDER_NAME = "LOGSTASH";


    @Bean
    public LogAutoConfiguration loggingFilter() {
        return new LogAutoConfiguration();
    }
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptorList = new ArrayList<ClientHttpRequestInterceptor>();
        restTemplate.setInterceptors(interceptorList);
        return restTemplate;
    }
    @Bean
    public LogstashTcpSocketAppender logstashAppender(LogProperties logProperties) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        LogstashTcpSocketAppender logstashTcpSocketAppender = new LogstashTcpSocketAppender();
        logstashTcpSocketAppender.setName(LOGSTASH_APPENDER_NAME);
        logstashTcpSocketAppender.setContext(loggerContext);
        logstashTcpSocketAppender.addDestination(logProperties.getAddress());
        LogstashEncoder encoder = new LogstashEncoder();
        encoder.setContext(loggerContext);
        encoder.setIncludeContext(true);
        encoder.setCustomFields("{\"appname\":\"" + logProperties.getName() + "\"}");
        encoder.start();
        logstashTcpSocketAppender.setEncoder(encoder);
        logstashTcpSocketAppender.start();
        loggerContext.getLogger(Logger.ROOT_LOGGER_NAME)
                     .addAppender(logstashTcpSocketAppender);
        return logstashTcpSocketAppender;
    }
}
