package cn.high.mx.framework.mq.core.binding;

import org.springframework.amqp.core.*;

public class BindingBuilder {
    public static Binding directBinding(Queue queue, DirectExchange exchange, String key) {
        return org.springframework.amqp.core.BindingBuilder.bind(queue).to(exchange).with(key);
    }

    public static Binding fanoutBindingOne(Queue queue, FanoutExchange exchange) {
        return org.springframework.amqp.core.BindingBuilder.bind(queue).to(exchange);
    }

    public static Binding topicBindingOne(Queue queue, TopicExchange exchange, String key) {
        return org.springframework.amqp.core.BindingBuilder.bind(queue).to(exchange).with(key);
    }

    public static Binding autoAckDirectBinding(Queue queue, DirectExchange exchange, String key) {
        return directBinding(queue,exchange,key);
    }

    public static Binding manualAckDirectBinding(Queue queue, DirectExchange exchange, String key) {
        return directBinding(queue,exchange,key);
    }

    public static Binding delayBinding(Queue queue, DirectExchange exchange, String key) {
        return directBinding(queue,exchange,key);
    }

    public static Binding priorityBinding(Queue queue, DirectExchange exchange, String key) {
        return directBinding(queue,exchange,key);
    }

}
