package cn.high.mx.framework.common.pefix;

public class OrderPrefix extends BasePrefix{
    public static OrderPrefix orderInfoPrefix;


    public static OrderPrefix orderCountPrefix;


    static {
        orderInfoPrefix = new OrderPrefix("mx.order.info");
        orderCountPrefix = new OrderPrefix("mx.order.count");
    }
    public OrderPrefix(String prefixValue) {
        super(prefixValue);
    }
}
