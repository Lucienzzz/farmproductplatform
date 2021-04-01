package com.zzq.farmproductplatform.controller;

import com.alibaba.fastjson.JSONObject;
import com.zzq.farmproductplatform.common.JWTUtils;
import com.zzq.farmproductplatform.entity.User;
import com.zzq.farmproductplatform.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private JWTUtils jwtUtils;

    @CrossOrigin
    @PostMapping("/user/login/go")
    @ResponseBody
    public JSONObject doLogin(String username, String password) {
        JSONObject jsonObject = new JSONObject();
        String token = jwtUtils.generateToken(username);
        jsonObject.put("token", token);
        jsonObject.put("totalResult", userLoginService.loginCounts(username, password));
        return jsonObject;
    }

    @PostMapping("/test")
    @ResponseBody
    public String doTest() {
        return "asd";
    }
}
