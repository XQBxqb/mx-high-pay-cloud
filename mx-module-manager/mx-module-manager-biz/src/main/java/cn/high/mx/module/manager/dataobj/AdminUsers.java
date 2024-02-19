package cn.high.mx.module.manager.dataobj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
* 用户表
* </p>
*
* @author  MX 
* @since 2023-11-26
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = false)
@TableName("admin_users")
public class AdminUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String name;


    private String avatar;

    private String phone;

    private String role;

    private String password;

    @TableField("is_ban")
    private Boolean isBan;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;

}
