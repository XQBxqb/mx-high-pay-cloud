package cn.high.mx.module.mission.mq.enums;

public enum OrderStatusEnum {

    WAITING_ORDER(1,"等待处理订单"),

    WORKING_ORDER(2,"正在处理订单"),

    FINISH_ORDER(3,"订单处理成功完成"),

    ERROR_ORDER(4,"异常订单，等待后台人员处理");
    public final Integer value;

    public final String descri;

    private OrderStatusEnum(Integer value, String descri) {
        this.value = value;
        this.descri = descri;
    }
}
