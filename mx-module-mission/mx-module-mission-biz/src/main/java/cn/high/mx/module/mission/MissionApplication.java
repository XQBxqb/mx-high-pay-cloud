package cn.high.mx.module.mission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MissionApplication {
    public static void main(String[] args) {
        SpringApplication.run(MissionApplication.class,args);
    }
}
