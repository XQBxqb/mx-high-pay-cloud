package cn.high.mx.module.manager.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ReqPatchAdminUser {
    @NotNull(message = "管理员id不能为空")
    private Integer id;

    @NotNull(message = "权限列表必须存在")
    private List<String> permissionIds;
}
