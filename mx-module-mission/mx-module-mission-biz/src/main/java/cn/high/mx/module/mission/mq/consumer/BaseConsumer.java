package cn.high.mx.module.mission.mq.consumer;

import cn.high.mx.framework.redis.config.RedissonService;
import cn.high.mx.module.mission.cache.redis.consts.RedisConst;
import cn.high.mx.module.mission.mq.dto.GoodsMsgDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseConsumer {
    public Long blankMsgError(GoodsMsgDto goodsMsg, Long goodId, Long orderId, Long userId, RedissonService redissonService) {
        if(goodId == null|| orderId == null|| userId ==null){
            log.error("common queue-发生异常：消息内容存在异常 , payload {}", goodsMsg);
            if(orderId != null){
                redissonService.set(RedisConst.PREFIX_ORDER_STATUS+":"+ orderId,null,RedisConst.DEFAULT_EXPIRE_TIME);
            }
            if(goodId !=null&& orderId !=null){
                orderId = goodId * 1000000 + userId;
                redissonService.set(RedisConst.PREFIX_ORDER_STATUS+":"+ orderId,null,RedisConst.DEFAULT_EXPIRE_TIME);
            }
            return null;
        }
        return orderId;
    }

}
