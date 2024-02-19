package cn.high.mx.module.uaa.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReqRegisterDto {
    @NotBlank(message = "注册手机号必须填写")
    private String registerMobile;
    @NotBlank(message = "注册密码必须填写")
    private String registerPassword;
}
