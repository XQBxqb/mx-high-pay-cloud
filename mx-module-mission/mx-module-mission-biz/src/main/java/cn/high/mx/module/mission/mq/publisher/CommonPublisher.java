package cn.high.mx.module.mission.mq.publisher;

import cn.high.mx.module.mission.framework.rabbitmq.consts.MqConsts;
import cn.high.mx.module.mission.mq.dto.GoodsMsgDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CommonPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publishMsg(GoodsMsgDto goodsMsgDto){
        try {
            rabbitTemplate.setExchange("common-exchange");
            rabbitTemplate.setRoutingKey(MqConsts.DIRECT_KEY);
            rabbitTemplate.convertAndSend(goodsMsgDto);
            log.info("publish msg to common-exchange");
        } catch (AmqpException e) {
            log.error("publish msg error ");
            throw new RuntimeException(e);
        }
    }
}
