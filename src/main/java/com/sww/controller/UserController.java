package com.sww.controller;

import com.sww.pojo.User;
import com.sww.service.UserService;
import com.sww.util.JwtUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sww
 */

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/token")
    public String print(@RequestBody User user) {
        String token = JwtUtil.generateToken(user);
        return token;
    }

    @RequiresRoles("admin")
    @GetMapping("/test")
    public String t1() {
        System.out.println("test");
        return "aaa";
    }
}
