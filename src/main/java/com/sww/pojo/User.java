package com.sww.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.sww.pojo.group.OnExtraConditionGroup;
import com.sww.pojo.group.OnInsertValidateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author sww
 */
@Data
@TableName("user")
@NoArgsConstructor
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    @Null
    @ApiModelProperty()
    private Long id;

    @TableField(value = "username")
    @NotNull(groups = {OnExtraConditionGroup.class, OnInsertValidateGroup.class}, message = "username不能为空")
    @Pattern(regexp = "[0-9a-zA-z]{3,20}")
    private String username;

    @TableField(value = "password")
    @NotNull(groups = {OnInsertValidateGroup.class}, message = "password不能为空")
    @Pattern(regexp = "[0-9a-zA-z]{6,20}")
    private String password;

    @NotNull(groups = {OnExtraConditionGroup.class}, message = "email不能为空")
    @Email(message = "email格式错误")
    private String email;

    @Null
    private String role;

    @TableField(fill = FieldFill.INSERT, select = false)
    @Null
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE, select = false)
    @Null
    private Date gmtModified;

    @TableLogic
    @TableField(value = "is_delete", select = false, fill = FieldFill.INSERT)
    @Null
    private Integer delete;


    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
