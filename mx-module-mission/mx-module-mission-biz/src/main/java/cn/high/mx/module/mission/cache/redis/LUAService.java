package cn.high.mx.module.mission.cache.redis;

import cn.high.mx.framework.redis.config.RedissonService;
import cn.high.mx.module.mission.cache.redis.consts.RedisConst;
import cn.high.mx.module.mission.dao.GoodsMapper;
import cn.high.mx.module.mission.dataobj.Goods;
import cn.high.mx.module.mission.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RScript;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class LUAService {
    private final Redisson redisson;

    private final GoodsMapper goodsMapper;

    private final RedissonService redissonService;

    private static final String orderJudgeScript = "local goodStockKey = KEYS[1]; " +
            "local orderStatusKey = KEYS[2]; " +
            "local endTime = tonumber(redis.call('get', KEYS[3])); " +
            "local currentTimeMillis = tonumber(ARGV[1]); " +
            "if currentTimeMillis > endTime then " +
            "    return -1; " +
            "end; " +
            "local stock = tonumber(redis.call('get', goodStockKey)); " +
            "local orderStatus = redis.call('get', orderStatusKey); " +
            "if stock ~= nil and stock > 0 and orderStatus == false then " +
            "   redis.call('decr', goodStockKey); " +
            "   redis.call('set', orderStatusKey, 1); " +
            "   return stock - 1 ; " +
            "else " +
            "   return -1; " +
            "end ; ";


    @PostConstruct
    public void init() {
        List<Goods> goodsList = goodsMapper.selectAll();
        goodsList.stream().forEach(t->{
            long enT = DateUtil.LocalDateTimeToTimeStamp(t.getEndTime());
            Integer goodsStock = t.getGoodsStock();
            redissonService.set(RedisConst.PREFIX_GOOD_END_TIME+":"+t.getId(),enT,RedisConst.DEFAULT_EXPIRE_TIME);
            redissonService.set(RedisConst.PREFIX_GOOD_NUMS+":"+t.getId(),goodsStock,RedisConst.DEFAULT_EXPIRE_TIME);
        });
    }

    public static void main(String[] args) {
        int nano = LocalDateTime.now()
                                .getNano();
        System.out.println(nano);
    }

    //首先根据goodId判断是否有库存数量大于0，然后根据orderId查询订单为null下,之后更新库存与订单状态->对应订单库存减1，订单状态赋值1，返回剩余库存，若二者任一不满足返回-1
    public Integer orderJudge(long goodId,String path) {
        RScript script = redisson.getScript();
        List<Object> list = new ArrayList<>();
        Object goodKey = RedisConst.PREFIX_GOOD_NUMS + ":" + goodId;
        Object pathKey = RedisConst.PREFIX_ORDER_STATUS + ":" + path;
        Object goodEndTime = RedisConst.PREFIX_GOOD_END_TIME + ":" + goodId;
        Object currentTimeMillis = System.currentTimeMillis();
        list.add(goodKey);
        list.add(pathKey);
        list.add(goodEndTime);
        Object res = script.eval(RScript.Mode.READ_WRITE, orderJudgeScript, RScript.ReturnType.INTEGER, list, currentTimeMillis);
        return (int) (long) res;
    }

}