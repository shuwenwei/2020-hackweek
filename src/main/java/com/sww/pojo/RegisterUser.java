package com.sww.pojo;

import com.sww.pojo.group.OnInsertValidateGroup;
import com.sww.pojo.group.OnUpdateValidateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 * @author sww
 */
@Data
public class RegisterUser {
    @NotNull(groups = {OnInsertValidateGroup.class, OnUpdateValidateGroup.class}, message = "用户名不能为空")
    @Pattern(regexp = "[0-9a-zA-z]{3,20}", message = "用户名长度为3-20,由字母和数字组成")
    private String username;
    @NotNull(groups = {OnInsertValidateGroup.class, OnUpdateValidateGroup.class}, message = "密码不能为空")
    @Pattern(regexp = "[0-9a-zA-z]{6,20}", message = "密码长度为6-20,由字母和数字组成")
    private String password;
    @NotNull(groups = {OnInsertValidateGroup.class}, message = "邮箱不能为空")
    @Email(message = "email格式错误")
    private String email;
    @NotNull(groups = {OnInsertValidateGroup.class, OnUpdateValidateGroup.class})
    @Length(min = 6, max = 6, message = "验证码为6位", groups = {OnInsertValidateGroup.class, OnUpdateValidateGroup.class})
    private String code;
}
