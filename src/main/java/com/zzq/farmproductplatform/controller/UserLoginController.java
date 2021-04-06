package com.zzq.farmproductplatform.controller;

import com.alibaba.fastjson.JSONObject;
import com.zzq.farmproductplatform.common.JWTUtils;
import com.zzq.farmproductplatform.common.JsonResult;
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

    @CrossOrigin
    @PostMapping("/user/login/go")
    @ResponseBody
    public JsonResult<String> doLogin(String username, String password) {
        userLoginService.loginCounts(username, password);
        return JsonResult.ok(JWTUtils.generateToken(username));
    }

    @PostMapping("/test")
    @ResponseBody
    public String doTest() {
        return "asd";
    }
}
