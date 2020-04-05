package com.sww.controller;

import com.alibaba.druid.sql.visitor.functions.Bin;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sww.exception.BadRequestException;
import com.sww.pojo.RegisterUser;
import com.sww.pojo.ResponseBean;
import com.sww.pojo.User;
import com.sww.pojo.group.OnInsertValidateGroup;
import com.sww.pojo.group.OnUpdateValidateGroup;
import com.sww.service.AsyncService;
import com.sww.service.UserService;
import com.sww.util.BindingResultUtil;
import com.sww.util.JwtUtil;
import com.sww.util.PasswordUtil;
import com.sww.util.ValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author sww
 */

@RestController
public class UserController {

    private UserService userService;
    private AsyncService asyncService;

    @Autowired
    public void setAsyncService(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/token")
    public ResponseBean login(@RequestBody User user, BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        String reqUsername = user.getUsername();
        String reqPassword = user.getPassword();
        User dbUser = userService.findUserByUsername(reqUsername);
        if (PasswordUtil.verify(reqPassword, dbUser.getPassword())) {
            return new ResponseBean("登录成功", JwtUtil.generateToken(dbUser), 1);
        }
        throw new BadRequestException("用户名或密码错误");
    }

    @PostMapping("/api/email")
    public ResponseBean sendRegisterEmail(@RequestBody User user, BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        String email = user.getEmail();
        String username = user.getUsername();
        if (!userService.checkUsername(username) && !userService.checkEmail(email)) {
            asyncService.sendMessage("注册", email);
            return new ResponseBean("发送成功", null, 1);
        }
        throw new BadRequestException("用户名或邮箱已存在");
    }

    @PostMapping("/api/register")
    public ResponseBean checkAndRegister(@RequestBody @Validated(value = OnInsertValidateGroup.class)
            RegisterUser registerUser , BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        String username = registerUser.getUsername();
        String email = registerUser.getEmail();

        if (userService.checkUsername(username) || userService.checkEmail(email)) {
            throw new BadRequestException("用户名或邮箱已被使用");
        }

        boolean success = ValidateCodeUtil.checkValidateCode(email, registerUser.getCode());
        if (success) {
            String password = PasswordUtil.generate(registerUser.getPassword());
            User user = new User(username, password, email);
            userService.save(user);
            return new ResponseBean("注册成功", null, 1);
        }
        throw new BadRequestException("注册失败");
    }

    @GetMapping("/api/forget/{username}")
    public ResponseBean forgetPassword(@PathVariable String username) {
        User dbUser = userService.findUserByUsername(username);
        if (dbUser == null) {
            throw new BadRequestException("用户名不存在");
        }
        String email = dbUser.getEmail();
        asyncService.sendMessage("重置密码", email);

        String past = email.split("@")[1];
        String viewEmail = email.substring(0,2);
        String msg = "已向" + viewEmail + "****" + "@" + past + "发送邮件";
        return new ResponseBean(msg, null, 1);
    }

    @PutMapping("/api/forget")
    public ResponseBean checkAndModify(@RequestBody @Validated(value = OnUpdateValidateGroup.class)
          RegisterUser registerUser, BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        String username = registerUser.getUsername();
        User dbUser = userService.findUserByUsername(username);
        String email = registerUser.getEmail();
        if (ValidateCodeUtil.checkValidateCode(email, registerUser.getCode())) {
            String generatedPassword = PasswordUtil.generate(registerUser.getPassword());
            dbUser.setPassword(generatedPassword);
            userService.update(new UpdateWrapper<User>(dbUser));
            return new ResponseBean("修改成功", null, 1);
        }
        throw new BadRequestException("修改失败");
    }

}
