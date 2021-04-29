package com.zzq.farmproductplatform.controller;

import com.alibaba.fastjson.JSONObject;
import com.zzq.farmproductplatform.common.JWTUtils;
import com.zzq.farmproductplatform.common.ValidateCode;
import com.zzq.farmproductplatform.entity.User;
import com.zzq.farmproductplatform.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;


@Controller
public class UserLoginController {

    private UserLoginService userLoginService;

    @Autowired
    public void setUserLoginService(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    private JWTUtils jwtUtils;

    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/login")
    public String Login() {
        return "login";
    }

    // 生成验证码
    @GetMapping("/code")
    public void getCode(HttpSession session, HttpServletResponse response) throws IOException {
        Map<String, Object> map = ValidateCode.generateCodeAndPic();
        session.setAttribute("ValidateCode", map.get("code"));
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("image/png");
        ImageIO.write((BufferedImage)map.get("codePic"), "png", os);
    }


    @PostMapping("/user/login/")
    @ResponseBody
    public JSONObject doLogin(User user) {
//        System.out.println(user);
        JSONObject jsonObject = new JSONObject();
        int count = userLoginService.loginCounts(user);
        jsonObject.put("totalResult", count);
        if (count != 0) {
            String token = jwtUtils.generateToken(user);
            jsonObject.put("token", token);
        }
        return jsonObject;
    }

    @PostMapping("/test")
    @ResponseBody
    public String doTest() {
        return "asd";
    }
    @GetMapping("/TestGet")
    @ResponseBody
    public String doGetTest() {
        return "asd";
    }
}
