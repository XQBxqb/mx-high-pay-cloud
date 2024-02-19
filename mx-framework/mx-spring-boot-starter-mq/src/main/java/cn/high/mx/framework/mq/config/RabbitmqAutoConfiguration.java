package cn.high.mx.framework.mq.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * RabbitMQ配置类
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RabbitmqAutoConfiguration {
    //真正的工厂类，用于创建消息监听容器（Message Listener Containers）
    private final CachingConnectionFactory connectionFactory;
    // 自动装配消息监听器所在的容器工厂配置类实例 在 Spring AMQP 中并不是一个“工厂”（Factory）类，而是一个配置器（Configurer）类
    private final SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;
    /**
     * 为单一消费者实例的配置
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer() {
        // 定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 设置容器工厂所用的实例
        factory.setConnectionFactory(connectionFactory);
        // 设置消息在传输中的格式，在这里采用JSON的格式进行传输
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        // 设置并发消费者实例的初始数量。在这里为1个
        factory.setConcurrentConsumers(1);
        // 设置并发消费者实例的最大数量。在这里为1个
        factory.setMaxConcurrentConsumers(1);
        // 设置并发消费者实例中每个实例拉取的消息数量-在这里为1个
        factory.setPrefetchCount(1);
        return factory;
    }

    /**
     * 多个消费者实例的配置，主要是针对高并发业务场景的配置
     * CachingConnectionFactory限制的并发消费者实例是针对一个消息队列监听容器，即使实例数量设置为1，不同消息队列监听容器还是能够并行进行消费
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        // 定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 设置容器工厂所用的实例
        factoryConfigurer.configure(factory,connectionFactory);
        // 设置消息在传输中的格式。在这里采用JSON的格式进行传输
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        // 设置消息的确认消费模式。NONE表示不需要确认消费;AUTO为自动确认;MANUAL为手动确认；自动确认在mq成功消费一条信息后回移除队列所有信息，而手动确认会只移除消费了的信息
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // 设置并发消费者实例的初始数量。在这里为10个
        factory.setConcurrentConsumers(10);
        // 设置并发消费者实例的最大数量。在这里为15个
        factory.setMaxConcurrentConsumers(15);
        // 设置并发消费者实例中每个实例拉取的消息数量。在这里为10个
        factory.setPrefetchCount(10);
        return factory;
    }

    // 自定义配置RabbitMQ发送消息的操作组件RabbitTemplate
        @Bean
        public RabbitTemplate rabbitTemplate() {
            // 设置“发送消息后进行确认”,当启用发布者确认（Publisher Confirms）时，RabbitMQ 服务器会在接收并处理完消息后向发布者发送一个确认（Ack）,在消息被确认之前，发布者可以选择保留消息，或者在未收到确认的情况下重试发送。
            connectionFactory.setPublisherConfirms(true);
            // 设置“发送消息后返回确认信息” 意味着如果一条消息无法被路由到一个有效的队列中，那么这条消息将被返回给发布者
            connectionFactory.setPublisherReturns(true);
            // 构造发送消息组件实例对象
            RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
            //当 setMandatory 设置为 true 时，如果发送的消息无法被路由到任何一个队列（例如，由于使用了不存在的交换器或路由键），RabbitMQ 服务器会将该消息返回给发送者。
            //如果设置为 false，则在消息无法路由时，它会被服务器静默丢弃，而发送者不会收到任何通知
            rabbitTemplate.setMandatory(true);
            // 发送消息后，如果发送成功，则输出“消息发送成功”的反馈信息
            rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause));
            // 发送消息后，如果发送失败，则输出“消息发送失败-消息丢失”的反馈信息
            rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message));
            // 定义消息传输的格式为JSON字符串格式
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            // 最终返回RabbitMQ的操作组件实例RabbitTemplate
            return rabbitTemplate;
        }

    /**
     * 确认消费模式为自动确认机制-AUTO,采用直连传输directExchange消息模型
     * 确认机制->消息被消费者接收，它会被视为已经成功处理，然后从消息队列中删除
     */
    @Bean
    public SimpleRabbitListenerContainerFactory singleListenerContainerAuto() {
        // 定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 设置容器工厂所用的实例
        factory.setConnectionFactory(connectionFactory);
        // 设置消息在传输中的格式，在这里采用JSON的格式进行传输
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        // 设置并发消费者实例的初始数量。在这里为1个
        factory.setConcurrentConsumers(1);
        // 设置并发消费者实例的最大数量。在这里为1个
        factory.setMaxConcurrentConsumers(1);
        // 设置并发消费者实例中每个实例拉取的消息数量-在这里为1个
        factory.setPrefetchCount(1);
        // 确认消费模式为自动确认机制
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }
    /**
     * 确认消费模式为手动确认机制-MANUAL,采用直连传输消息模型
     */
    @Bean
    public SimpleRabbitListenerContainerFactory singleListenerContainerManual() {
        // 定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 设置容器工厂所用的实例
        factory.setConnectionFactory(connectionFactory);
        // 设置消息在传输中的格式，在这里采用JSON的格式进行传输
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        // 设置并发消费者实例的初始数量。在这里为1个
        factory.setConcurrentConsumers(1);
        // 设置并发消费者实例的最大数量。在这里为1个
        factory.setMaxConcurrentConsumers(1);
        // 设置并发消费者实例中每个实例拉取的消息数量-在这里为1个
        factory.setPrefetchCount(1);
        // 确认消费模式为手动确认机制
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}
