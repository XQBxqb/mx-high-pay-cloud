package cn.high.mx.module.manager.dto;

import cn.high.mx.module.manager.dataobj.PermissionMenu;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionMenuDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer level;

    private Integer sort;

    private Integer permissionId;

    private String key;

    private String name;

    private String icon;

    private PermissionDTO permission;

    private List<PermissionMenuDTO> children ;

    public PermissionMenuDTO(String json) throws JsonProcessingException {
        this(new ObjectMapper().readValue(json, PermissionMenuDTO.class));
    }
    public PermissionMenuDTO(PermissionMenuDTO permissionMenuDTO){
        this(permissionMenuDTO.getId(),permissionMenuDTO.getLevel(),permissionMenuDTO.getSort(),permissionMenuDTO.getPermissionId(),permissionMenuDTO.getKey(),permissionMenuDTO.getName(),permissionMenuDTO.getIcon(),permissionMenuDTO.getPermission(),permissionMenuDTO.getChildren());
    }
}
