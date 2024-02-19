package cn.high.mx.module.mission.exception.enums;

public enum SysStatusEnum implements BaseStatusEnum {
    RES_SUCCESS(200,"success"),
    RES_FILE_DELETE_ERROR(201,"文件删除异常，请联系后台管理员")
    ;
    private Integer code;
    private String message;

    SysStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    @Override
    public Integer getCode() {
        return code;
    }
    @Override
    public String getMessage() {
        return message;
    }

}
