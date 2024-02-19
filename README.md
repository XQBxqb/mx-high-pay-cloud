
商品秒杀系统由秒杀系统和后台管理系统组成,秒杀系统使用微服务架构,解决用户参与秒杀活动，出现重复秒杀的问题,后台管理系统则是管理用户权限与商品下单信息等

技术选型: SpringBoot、SpringCloud、Redis、RabbitMq、HDFS、Shiro、Nacos、Shiro、sentinel

技术描述：  
基于JWT、Shiro实现单点登录、认证、授权功能  
设计秒杀接口幂等,使得用户下单秒杀商品唯一,避免重复下单  
采用Redis LUA脚本解决高并发下商品超卖问题   
使用RabbitMq解耦上下游业务逻辑、高并发削峰  
基于HDFS实现分布式文件系统  
利用JWT、Shiro实现单点登录与权限管理    
采用docker部署各组件,dockercompose部署应用系统模块  

模块信息：
mx-framework:自定义starter模块，自定义组件配置实现复用  
mx-module-gateway:网关模块，统一路由转发，也设计了sentinel限流各接口    
mx-module-manager:后台管理模块，对用户、管理员、商品进行后台管理    
mx-module-mission:商品秒杀活动模块，处理商品秒杀    
mx-module-sba:spring-boot-admin监控模块，可以查看模块的健康信息 
mx-module-uaa:用户登录模块  

## License

This project is licensed under the Apache License 2.0 - see the LICENSE.txt file for details.