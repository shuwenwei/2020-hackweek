package com.sww.controller;

import com.sww.exception.BadRequestException;
import com.sww.pojo.RegisterUser;
import com.sww.pojo.ResponseBean;
import com.sww.pojo.User;
import com.sww.pojo.group.OnExtraConditionGroup;
import com.sww.pojo.group.OnInsertValidateGroup;
import com.sww.pojo.group.OnUpdateValidateGroup;
import com.sww.service.AsyncService;
import com.sww.service.UserService;
import com.sww.util.BindingResultUtil;
import com.sww.util.JwtUtil;
import com.sww.util.PasswordUtil;
import com.sww.util.ValidateCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 已经全部测试过
 * @author sww
 */
@Api(value = "注册，登录，请求发送邮件，忘记密码")
@RestController
@RequestMapping("/account")
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

    /**
     * @param user username password
     */
    @ApiOperation(value = "获取token")
    @PostMapping("/token")
    public ResponseBean login(@RequestBody @ApiParam(required = true) @Validated(OnInsertValidateGroup.class) User user
            , BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        String reqUsername = user.getUsername();
        String reqPassword = user.getPassword();
        User dbUser = userService.findUserByUsername(reqUsername);
        if (PasswordUtil.verify(reqPassword, dbUser.getPassword())) {
            return new ResponseBean("登录成功", JwtUtil.generateToken(dbUser), 1);
        }
        throw new BadRequestException("用户名或密码错误");
    }


    /**
     * @param user  需要username和email
     */
    @PostMapping("/email")
    public ResponseBean sendRegisterEmail(@RequestBody @Validated(OnExtraConditionGroup.class) User user
            , BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        String email = user.getEmail();
        String username = user.getUsername();
        if (!userService.checkUsername(username) && !userService.checkEmail(email)) {
            asyncService.sendMessage("注册", email);
            return new ResponseBean("发送成功", null, 1);
        }
        throw new BadRequestException("用户名或邮箱已存在");
    }

    /**
     * @param registerUser 需要username password email 验证码
     */
    @PostMapping("/register")
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
            userService.addUser(user);
            return new ResponseBean("注册成功", null, 1);
        }
        throw new BadRequestException("注册失败");
    }

    @GetMapping("/forget")
    public ResponseBean forgetPassword(@RequestParam String username) {
        System.out.println(username);
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

    /**
     * @param registerUser 需要username password 和 验证码
     */
    @PutMapping("/forget")
    public ResponseBean checkAndModify(@RequestBody @Validated(value = OnUpdateValidateGroup.class)
          RegisterUser registerUser, BindingResult bindingResult) {
        BindingResultUtil.checkBinding(bindingResult);

        System.out.println(registerUser);
        String username = registerUser.getUsername();
        User dbUser = userService.findUserByUsername(username);
        String email = dbUser.getEmail();
        if (ValidateCodeUtil.checkValidateCode(email, registerUser.getCode())) {
            String generatedPassword = PasswordUtil.generate(registerUser.getPassword());
            dbUser.setPassword(generatedPassword);
            System.out.println(dbUser);
            userService.updateById(dbUser);
            return new ResponseBean("修改成功", null, 1);
        }
        throw new BadRequestException("修改失败");
    }

}
