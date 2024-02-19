package cn.high.mx.module.manager.dto;

import lombok.Data;

@Data
public class ReqRole {
    private Integer id;

    private String role;

    private String roleName;

    /**
     * 权限ids
     */
    private String[] permissionIds;

}
