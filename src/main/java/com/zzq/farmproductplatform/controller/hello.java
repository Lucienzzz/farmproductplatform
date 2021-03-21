package com.zzq.farmproductplatform.controller;

import com.zzq.farmproductplatform.entity.User;
import com.zzq.farmproductplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class hello {
    @RequestMapping("hello")
    public String a() {
        return "ad";
    }

    @Autowired
    private UserService userService;

    @GetMapping("/data")
    public List<User> aa() {
        return userService.alllist();
    }
}
