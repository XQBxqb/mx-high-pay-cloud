package cn.high.mx.module.manager.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class ReqPermission {

    private Integer id;

    /**
     * 权限
     */
    @NotBlank(message = "权限内容不能为空")
    private String permission;

    /**
     * 权限名称
     */
    @NotBlank(message = "权限名必须存在")
    private String permissionName;


}
