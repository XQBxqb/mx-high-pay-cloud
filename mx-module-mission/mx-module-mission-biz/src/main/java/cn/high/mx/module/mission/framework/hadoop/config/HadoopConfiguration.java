package cn.high.mx.module.mission.framework.hadoop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HadoopConfiguration {
    @Bean
    public org.apache.hadoop.conf.Configuration configuration(){
        return new org.apache.hadoop.conf.Configuration();
    }
}
