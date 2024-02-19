package cn.high.mx.module.manager.dto;

import cn.high.mx.module.manager.utils.date.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDTO implements Serializable {
    public static final long serialVersionUID = 1L;
    private Integer id;

    private String username;

    private String name;


    private String avatar;

    private String phone;

    private String role;

    private String password;

    private Boolean isBan;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    public AdminUserDTO(String json) throws JsonProcessingException {
        this(new ObjectMapper().readValue(json, AdminUserDTO.class));
    }
    public AdminUserDTO(AdminUserDTO adminUserDTO){
        this(adminUserDTO.getId(), adminUserDTO.getUsername(), adminUserDTO.getName(), adminUserDTO.getAvatar(), adminUserDTO.getPhone(), adminUserDTO.getRole(), adminUserDTO.getPassword(), adminUserDTO.getIsBan(), adminUserDTO.getCreatedAt(), adminUserDTO.getUpdatedAt());
    }
}
