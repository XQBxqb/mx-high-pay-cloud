package cn.high.mx.module.uaa.dto;

import cn.high.mx.framework.common.exception.enums.BaseStatusEnum;
import cn.high.mx.framework.common.exception.enums.BizStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResUserCommonDto {
    private Integer code;

    private String msg;

    public ResUserCommonDto(BaseStatusEnum baseStatusEnum) {
        this.code = baseStatusEnum.getCode();
        this.msg = baseStatusEnum.getMessage();
    }

    public static ResUserCommonDto buildUserCommon(BaseStatusEnum baseStatusEnum){
        return new ResUserCommonDto(baseStatusEnum);
    }
}
