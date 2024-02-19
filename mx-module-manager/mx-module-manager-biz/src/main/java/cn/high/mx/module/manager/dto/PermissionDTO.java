package cn.high.mx.module.manager.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PermissionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String permission;

    private String permissionName;
    public PermissionDTO(String json) throws JsonProcessingException {
        this(new ObjectMapper().readValue(json, PermissionDTO.class));
    }
    public PermissionDTO(PermissionDTO permissionDTO){
        this(permissionDTO.getId(),permissionDTO.getPermission(),permissionDTO.getPermissionName());
    }
}
