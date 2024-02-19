package cn.high.mx.framework.common.exception.enums;

/**
 * @author 昴星
 * @date 2023-09-02 22:12
 * @explain
 */

public enum BizStatusEnum implements BaseStatusEnum{

    RES_SUCCESS(200,"success","success"),

    RES_TOKEN_ERROR(201,"验证令牌异常","false"),
    RES_RUNTIME_ERROR(202,"业务异常","false"),
    RES_PARAM_INCORRECT(203,"效验参数失败","false"),
    RES_PASSWORD_INCORRECT(204,"密码错误","false"),
    RES_USER_NOT_EXIST(205,"不存在该用户名用户","false"),

    RES_REGISTER_PHONE_EXISTED(206,"手机号已注册","false"),
    RES_TOKEN_BLANK(207,"令牌不存在，请登录","false"),
    RES_ERROR_GOOD_NOT_EXIST(208,"商品不存在","false"),

    RES_ERROR_EXIT_USER_OWN_DELETE_ROLE(209,"存在用户拥有要删除的角色","false"),

    RES_IDS_BLANK(210,"用户名输入有误，请重新尝试","false");




    private Integer code;
    private String message;

    private String success;

    BizStatusEnum(Integer code, String message,String success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }
    public Integer getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }

    @Override
    public String getSuccess() {
        return success;
    }
}
