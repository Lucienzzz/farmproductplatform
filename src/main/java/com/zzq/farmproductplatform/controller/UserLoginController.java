package com.zzq.farmproductplatform.controller;

import com.zzq.farmproductplatform.entity.User;
import com.zzq.farmproductplatform.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/user/login")
    public int doLogin(User user) {
        return userLoginService.loginCounts(user);
    }
}
