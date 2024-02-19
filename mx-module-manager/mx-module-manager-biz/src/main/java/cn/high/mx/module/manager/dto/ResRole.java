package cn.high.mx.module.manager.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResRole {
    private Integer id;

    private String role;

    private String roleName;

    /**
     * 权限ids
     */
    private List<PermissionDTO> permissions;
}
