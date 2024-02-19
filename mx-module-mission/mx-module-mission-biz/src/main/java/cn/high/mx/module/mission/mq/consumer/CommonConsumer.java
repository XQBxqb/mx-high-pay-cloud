package cn.high.mx.module.mission.mq.consumer;

import cn.high.mx.framework.redis.config.RedissonService;
import cn.high.mx.module.mission.dao.OrderInfoMapper;
import cn.high.mx.module.mission.dataobj.OrderInfo;
import cn.high.mx.module.mission.cache.redis.consts.RedisConst;
import cn.high.mx.module.mission.exception.BizException;
import cn.high.mx.module.mission.exception.enums.BizStatusEnum;
import cn.high.mx.module.mission.mq.dto.GoodsMsgDto;
import cn.high.mx.module.mission.mq.enums.OrderStatusEnum;
import cn.high.mx.module.mission.mq.publisher.CommonPublisher;
import cn.high.mx.module.mission.mq.publisher.DelayPublisher;
import cn.high.mx.module.mission.utils.SM3Util;
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
public class CommonConsumer extends BaseConsumer{
    private final RedissonService redissonService;
    
    private final OrderInfoMapper orderInfoMapper;
    
    private final CommonPublisher commonPublisher;
    
    private final DelayPublisher delayPublisher;

    @RabbitListener(queues = "common-queue", containerFactory = "singleListenerContainer")
    @Transactional(rollbackFor = Exception.class)
    public void consumeMsg(GoodsMsgDto goodsMsg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
        Long goodId = goodsMsg.getGoodId();
        Long orderId = goodsMsg.getOrderId();
        Long userId = goodsMsg.getUserId();
        //消息无效，错误消息，应该重新发送
        orderId = blankMsgError(goodsMsg, goodId, orderId, userId,redissonService);
        if (orderId == null) return;
        //这里操作是为了即使客户端不能够重复下单，但是可以实现相应的接口允许重复发送消息，但相对应的，也要防止消息的重复处理
        if(judgeStatus(goodsMsg, orderId))
            return;
        try {
            log.info("common queue-监听消息：{} ", JSONUtil.toJsonStr(goodsMsg));
            redissonService.set(RedisConst.PREFIX_ORDER_STATUS+":"+orderId, OrderStatusEnum.WORKING_ORDER.value,RedisConst.DEFAULT_EXPIRE_TIME);
            OrderInfo order = OrderInfo.builder()
                                       .id(orderId)
                                       .userId(userId)
                                       .goodsId(goodId)
                                       .build();
            orderInfoMapper.insert(order);
            channel.basicAck(tag, false);
            redissonService.set(RedisConst.PREFIX_ORDER_STATUS+":"+orderId,OrderStatusEnum.FINISH_ORDER.value,RedisConst.DEFAULT_EXPIRE_TIME);
        } catch (Exception e) {
            log.error("msg error , publish msg to delay queue ,error {}",e.getMessage(),e);
            redissonService.set(RedisConst.PREFIX_ORDER_STATUS+":"+orderId,OrderStatusEnum.ERROR_ORDER.value,RedisConst.DEFAULT_EXPIRE_TIME);
            delayPublisher.publisherMsg(goodsMsg);
            channel.basicAck(tag, true);
        }
    }


    private boolean judgeStatus(GoodsMsgDto goodsMsg, Long orderId) {
        String path = generatorPath(goodsMsg.getUserId(), goodsMsg.getGoodId());
        Integer status = redissonService.get(RedisConst.PREFIX_ORDER_STATUS + ":" + path, Integer.class);
        if(status != OrderStatusEnum.WAITING_ORDER.value){
            if(status.equals(OrderStatusEnum.WORKING_ORDER.value)){
                //重新发送消息，直到消息成功处理或者其他情况
                log.warn("order status 2 repeat publish msg");
                commonPublisher.publishMsg(goodsMsg);
                return true;
            }
            if(status.equals(OrderStatusEnum.FINISH_ORDER.value)){
                //消息已经成功处理，这里直接返回就行，避免重复消息处理
                log.info("pasted msg success handler");
                return true;
            }
            if(status.equals(OrderStatusEnum.ERROR_ORDER.value)){
                //向延迟队列发送消息
                delayPublisher.publisherMsg(goodsMsg);
                log.warn("msg error , publish msg to delay queue ");
            }
            return true;
        }
        return false;
    }
    private String generatorPath(Long uid,Long goodId){
        if(uid == null||goodId == null){
            throw new BizException(BizStatusEnum.RES_PATH_GENERATOR_ERROR);
        }
        return SM3Util.sm3(String.valueOf(uid) + "-" + String.valueOf(goodId));
    }

}
