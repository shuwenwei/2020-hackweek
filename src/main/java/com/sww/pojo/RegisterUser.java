package com.sww.pojo;

import com.sww.pojo.group.OnInsertValidateGroup;
import com.sww.pojo.group.OnUpdateValidateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


/**
 * @author sww
 */
@Data
public class RegisterUser {
    @NotNull(groups = {OnInsertValidateGroup.class}, message = "用户名不能为空")
    private String username;
    @NotNull(groups = {OnInsertValidateGroup.class}, message = "密码不能为空")
    private String password;
    @NotNull(groups = {OnInsertValidateGroup.class, OnUpdateValidateGroup.class}, message = "邮箱不能为空")
    private String email;
    @NotNull
    @Length(min = 6, max = 6, message = "验证码为6位")
    private String code;
}
