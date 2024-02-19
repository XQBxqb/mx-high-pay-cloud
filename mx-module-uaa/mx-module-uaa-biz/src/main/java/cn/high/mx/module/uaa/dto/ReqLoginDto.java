package cn.high.mx.module.uaa.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReqLoginDto {
    @NotBlank(message = "手机号必须填写")
    String mobile;
    @NotBlank(message = "密码必须填写")
    String password;
}
