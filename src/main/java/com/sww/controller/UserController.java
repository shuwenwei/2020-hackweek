package com.sww.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/token")
    public String print() {
        System.out.println("test");
        return "aaa";
    }
}
