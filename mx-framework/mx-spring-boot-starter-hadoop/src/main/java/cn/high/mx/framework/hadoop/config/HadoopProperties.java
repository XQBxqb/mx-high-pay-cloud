package cn.high.mx.framework.hadoop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties("mx.hadoop")
public class HadoopProperties {
    private String node;
}
