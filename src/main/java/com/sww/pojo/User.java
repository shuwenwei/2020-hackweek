package com.sww.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.sww.pojo.group.OnInsertValidateGroup;
import com.sww.pojo.group.OnUpdateValidateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * @author sww
 */
@Data
@TableName("user")
@NoArgsConstructor
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @TableField(value = "username")
    @NotNull(groups = {OnUpdateValidateGroup.class})
    private String username;
    @TableField(value = "password")
    @NotNull(groups = {OnUpdateValidateGroup.class})
    private String password;
    private String email;
    @Null
    private String role;
    @TableField(fill = FieldFill.INSERT, select = false)
    private Date gmtCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    private Date gmtModified;
    @TableField(value = "is_delete", select = false)
    private Integer delete;


    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
