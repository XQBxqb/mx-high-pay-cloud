package cn.high.mx.module.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String name;


    private String avatar;

    private String phone;

    private String role;


    private Boolean isBan;

    List<Authority> authorities;

    List<PermissionDTO> permissions;

    List<RoleDTO> roles;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Authority{
        private String authority;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleDTO {
        private Integer id;
        /**
         * 角色
         */
        private String role;

        /**
         * 角色名称
         */
        private String roleName;
    }
}
