package cn.high.mx.module.mission.framework.rabbitmq;

import cn.high.mx.framework.mq.core.binding.BindingBuilder;
import cn.high.mx.framework.mq.core.exchange.ExchangeBuilder;
import cn.high.mx.framework.mq.core.queue.QueueBuilder;

import cn.high.mx.module.mission.framework.rabbitmq.consts.MqConsts;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingConfig {
    @Bean
    public Queue commonQueue(){
        return QueueBuilder.directQueue("common-queue");
    }
    @Bean
    public DirectExchange commonExchange(){
        return ExchangeBuilder.directExchange("common-exchange");
    }
    @Bean
    public Binding dingBasic(){
        return BindingBuilder.directBinding(commonQueue(),commonExchange(),MqConsts.DIRECT_KEY);
    }
    @Bean
    public Queue deadQueue(){
        return QueueBuilder.deadQueue("dead-queue");
    }
    @Bean
    public DirectExchange deadExchange(){
        return ExchangeBuilder.deadExchange("dead-exchange");
    }
    @Bean
    public Binding bindingDead(){
        return BindingBuilder.directBinding(deadQueue(),deadExchange(), MqConsts.DEAD_KEY);
    }
    @Bean
    public Queue delayQueue(){
        return QueueBuilder.delayQueuePre("delay-queue","dead-exchange",MqConsts.DEAD_KEY,MqConsts.DEFAULT_TIME_STANDARD);
    }
    @Bean
    public DirectExchange delayExchange(){
        return ExchangeBuilder.delayExchangePre("delay-exchange");
    }
    @Bean
    public Binding bindingDelay(){
        return BindingBuilder.delayBinding(delayQueue(),delayExchange(),MqConsts.DELAY_KEY);
    }
}
