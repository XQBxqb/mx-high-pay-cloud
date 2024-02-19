package cn.high.mx.module.mission.mq.consumer;

import cn.high.mx.framework.redis.config.RedissonService;
import cn.high.mx.module.mission.dao.OrderInfoMapper;
import cn.high.mx.module.mission.dataobj.OrderInfo;
import cn.high.mx.module.mission.cache.redis.consts.RedisConst;
import cn.high.mx.module.mission.mq.dto.GoodsMsgDto;
import cn.high.mx.module.mission.mq.enums.OrderStatusEnum;
import cn.high.mx.module.mission.mq.publisher.DelayPublisher;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DeadConsumer extends BaseConsumer{
    private final RedissonService redissonService;

    private final OrderInfoMapper orderInfoMapper;

    private final DelayPublisher delayPublisher;

    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = "dead-queue", containerFactory = "singleListenerContainer")
    public void consumeMsg(GoodsMsgDto goodsMsg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
        try {
            log.info(" dead-queue-监听消息：{} ", JSONUtil.toJsonStr(goodsMsg));
            long userId = goodsMsg.getUserId();
            long goodsId = goodsMsg.getGoodId();
            Long orderId = goodsMsg.getOrderId();
            orderId = blankMsgError(goodsMsg, goodsId, orderId, userId, redissonService);
            if (orderId == null) return;
            OrderInfo orderInfo = OrderInfo.builder()
                                       .id(orderId)
                                       .goodsId(goodsId)
                                       .userId(userId)
                                       .build();
            orderInfoMapper.insert(orderInfo);
            log.info("dead-queue success handler msg");
            redissonService.set(RedisConst.PREFIX_ORDER_STATUS+":"+orderId, OrderStatusEnum.FINISH_ORDER.value,RedisConst.DEFAULT_EXPIRE_TIME);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            // 第二个参数requeue重新归入队列,true的话会重新归入队列,需要人为地处理此次异常消息,重新归入队列也会继续异常
            delayPublisher.publisherMsg(goodsMsg);
            log.error("dead-queue-发生异常：", e);
        }
    }
}
