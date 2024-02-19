package cn.high.mx.framework.druid.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties("spring.datasource.druid")
public class DruidProperties {
    private String dbUrl;
    private String username;
    private String password;
    private String driverClassName;
    private int initialSize;
    private int maxActive;
    private int minIdle;
    private int maxWait;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private int maxEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private String filters;
    private String connectionProperties;

}
