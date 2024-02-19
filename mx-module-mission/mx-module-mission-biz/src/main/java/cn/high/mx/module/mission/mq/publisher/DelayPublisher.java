package cn.high.mx.module.mission.mq.publisher;

import cn.high.mx.module.mission.framework.rabbitmq.consts.MqConsts;
import cn.high.mx.module.mission.mq.dto.GoodsMsgDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DelayPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publisherMsg(GoodsMsgDto goodsMsgDto){
        try {
            rabbitTemplate.setExchange("delay-exchange");
            rabbitTemplate.setRoutingKey(MqConsts.DELAY_KEY);
            rabbitTemplate.convertAndSend(goodsMsgDto);
            log.info("delay exchange publisher success");
        } catch (AmqpException e) {
            log.error("publish msg error ");
            throw new RuntimeException(e);
        }
    }
}
