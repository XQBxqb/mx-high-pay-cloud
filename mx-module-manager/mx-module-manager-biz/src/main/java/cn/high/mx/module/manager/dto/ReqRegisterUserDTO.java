package cn.high.mx.module.manager.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReqRegisterUserDTO {

    @NotBlank(message = "名称是必须的")
    private String username;
    @NotBlank(message = "密码是必须的")
    private String password;
    @NotBlank(message = "验证密码是必须的")
    private String confirmPassword;

    private String name;

    private String phone;
    @NotNull(message = "roleId必须存在")
    private Integer roleId;

    private Integer isBan;
}
