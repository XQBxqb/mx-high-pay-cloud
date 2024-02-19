package cn.high.mx.framework.mq.core.queue;


import org.springframework.amqp.core.Queue;

import java.util.HashMap;
import java.util.Map;

public class QueueBuilder {
    private static Queue buildBaseQueue(String queueName){
        return new Queue(queueName,true);
    }
    public static Queue directQueue(String queueName) {
        return buildBaseQueue(queueName);
    }

    public static Queue fanoutQueue(String queueName) {
        return buildBaseQueue(queueName);
    }

    public static Queue topicQueue(String queueName) {
        return buildBaseQueue(queueName);
    }

    public static Queue delayQueuePre(String queueName,String exchangeName,String key,Long ttl) {
        // 创建延迟队列的组成成分map，用于存放组成成分的相关成员
        Map<String, Object> args = new <String, Object>HashMap();
        // 设置消息过期之后的死信交换机(真正消费的交换机)
        args.put("x-dead-letter-exchange", exchangeName);
        // 设置消息过期之后死信队列的路由(真正消费的路由)
        args.put("x-dead-letter-routing-key", key);
        // 设定消息的TTL，单位为ms，在这里指的是s
        args.put("x-message-ttl", ttl);
        return new Queue(queueName, true,false,false, args);
    }

    public static Queue priorityQueue(String queueName,Integer priority) {
        Map<String, Object> args = new <String, Object>HashMap<String, Object>(1);
        // 设置消息优先级,有10个等级,消息不设置优先级默认为0
        args.put("x-max-priority", priority);
        return new Queue(queueName, true, false, false, args);
    }

    public static Queue deadQueue(String queueName){
        return buildBaseQueue(queueName);
    }
}
