package cn.high.mx.framework.hadoop.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;

@Configuration
@Slf4j
@EnableConfigurationProperties(HadoopProperties.class)
public class HadoopAutoConfiguration {
    @Bean
    @ConditionalOnBean(org.apache.hadoop.conf.Configuration.class)
    public FileSystem fileSystem(org.apache.hadoop.conf.Configuration configuration,HadoopProperties hadoopProperties){
        FileSystem fileSystem = null;
        try {
            fileSystem=FileSystem.get(URI.create(hadoopProperties.getNode()), configuration);
        } catch (IOException e) {
            log.error("System error "+e.getClass().getSimpleName()+" "+e.getMessage(),e);
        }
        return fileSystem;
    }
}
