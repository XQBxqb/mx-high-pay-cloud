package cn.high.mx.framework.mq.core.exchange;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;

public class ExchangeBuilder {
    public static DirectExchange directExchange(String exchangeName) {
        return new DirectExchange(exchangeName,true,false);
    }

    public static FanoutExchange fanoutExchange(String exchangeName) {
        return new FanoutExchange(exchangeName,true,false);
    }

    public static TopicExchange topicExchange(String exchangeName) {
        return new TopicExchange(exchangeName,true,false);
    }

    public static DirectExchange autoAckDirectExchange(String exchangeName) {
        return directExchange(exchangeName);
    }
    public static DirectExchange manualAckDirectExchange(String exchangeName) {
        return directExchange(exchangeName);
    }
    public static DirectExchange delayExchangePre(String exchangeName) {
        return directExchange(exchangeName);
    }
    public static DirectExchange priorityExchange(String exchangeName) {
        return directExchange(exchangeName);
    }
    public static DirectExchange deadExchange(String exchangeName){
        return directExchange(exchangeName);
    }
}
