package cn.high.mx.module.mission.res;


import cn.high.mx.framework.common.exception.enums.BizStatusEnum;
import cn.high.mx.module.mission.exception.enums.BaseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 昴星
 * @date 2023-09-02 22:07
 * @explain
 */
@Data
@AllArgsConstructor
public class RestRes<T> {
    private String message;

    private Integer code;

    private T data;

    private RestRes() {
        message= BizStatusEnum.RES_SUCCESS.getMessage();
        code=BizStatusEnum.RES_SUCCESS.getCode();
    }

    private RestRes(T data) {
        this();
        this.data = data;
    }

    private RestRes(Integer code, T data){
        this.code=code;
        this.data=data;
    }

    private RestRes(BaseStatusEnum statusEnum){
        message= statusEnum.getMessage();
        code=statusEnum.getCode();
    }

    public RestRes(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public static <T> RestRes<T> ok(){
        return new RestRes<>();
    }

    public static <T> RestRes<T> ok(T data){
        return new RestRes<>(data);
    }

    public static <T> RestRes<T> error(String message,Integer code){
        return new RestRes<>(message,code);
    }

    public static <T> RestRes<T> errors(String message,Integer code,T data){
        return new RestRes<>(message,code,data);
    }



    public static <T> RestRes<T> errorEnum(BaseStatusEnum statusEnum){
        return new RestRes<>(statusEnum);
    }
}
