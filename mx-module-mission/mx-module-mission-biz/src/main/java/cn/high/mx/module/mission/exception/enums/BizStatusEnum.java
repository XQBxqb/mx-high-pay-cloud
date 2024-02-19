package cn.high.mx.module.mission.exception.enums;

/**
 * @author 昴星
 * @date 2023-09-02 22:12
 * @explain
 */

public enum BizStatusEnum implements BaseStatusEnum {

    RES_SUCCESS(200,"success"),

    RES_TOKEN_ERROR(201,"验证令牌异常"),
    RES_RUNTIME_ERROR(202,"业务异常"),
    RES_PARAM_INCORRECT(203,"效验参数失败"),
    RES_PASSWORD_INCORRECT(204,"密码错误"),
    RES_USER_NOT_EXIST(205,"不存在该用户名用户"),

    RES_REGISTER_PHONE_EXISTED(206,"手机号已注册"),
    RES_TOKEN_BLANK(207,"令牌不存在，请登录"),
    RES_PATH_GENERATOR_ERROR(208,"参数丢失导致生成路径失败"),
    RES_PATH_AUTHEN_ERROR(209,"路径效验异常"),
    RES_ORDER_EXIST(210,"订单已存在，请不要重复下单"),
    RES_GOOD_COMPLETE(211,"商品已下架"),

    RES_GOOD_ORDER_NOT_EXIST(212,"没有该商品订单"),
    RES_ORDER_FAILED(213,"订单失败");





    private Integer code;
    private String message;


    BizStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }


}
