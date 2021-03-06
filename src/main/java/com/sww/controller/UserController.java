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
import com.sww.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;


/**
 * 已经全部测试过
 * @author sww
 */
@Api(value = "注册，登录，请求发送邮件，忘记密码")
@RestController
@RequestMapping("/account")
@RequiresRoles("user")
public class UserController {

    private UserService userService;
    private AsyncService asyncService;
    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

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
            String key = "token::" + dbUser.getId();
            if (redisUtil.hasKey(key)) {
                return new ResponseBean("登录成功", redisUtil.get(key), 1);
            }
            String token = JwtUtil.generateToken(dbUser);
//            在缓存中放入token
            redisUtil.set("token::" + dbUser.getId(), token, 60*60*2);
            return new ResponseBean("登录成功", token, 1);
        }
        throw new BadRequestException("用户名或密码错误");
    }


    /**
     * 已测试
     */
    @GetMapping("/code")
    public ResponseBean sendRegisterEmail(@RequestParam @Email String email) {

        if (!userService.checkEmail(email)) {
            asyncService.sendMessage("注册", email);
            return new ResponseBean("发送成功,十分钟内有效", null, 1);
        }
        throw new BadRequestException("邮箱已存在");
    }

    /**
     * @param registerUser 需要username password email 验证码
     */
    @PostMapping("/user")
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

    @GetMapping("/password/code")
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
        String msg = "已向" + viewEmail + "****" + "@" + past + "发送邮件,十分钟内有效";
        return new ResponseBean(msg, null, 1);
    }

    /**
     * @param registerUser 需要username password 和 验证码
     */
    @PutMapping("/password")
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
