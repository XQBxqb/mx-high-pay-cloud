package cn.high.mx.module.mission.framework.redis;


import cn.high.mx.framework.redis.config.RedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@Slf4j
public class RedisConnectionFactoryAutoConfiguration {
    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisProperties.getHost());
        redisConfig.setPort(Integer.parseInt(redisProperties.getPort()));
        redisConfig.setDatabase(Integer.parseInt(redisProperties.getDatabase()));
        redisConfig.setPassword(redisProperties.getPassword());
        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    public Config config(RedisProperties redisProperties) {
        Config config = new Config();
        config.setCodec(new StringCodec());
        config.useSingleServer()
              .setAddress("redis://"+redisProperties.getHost()+":"+redisProperties.getPort()).setPassword(redisProperties.getPassword());
        return config;
    }
}
