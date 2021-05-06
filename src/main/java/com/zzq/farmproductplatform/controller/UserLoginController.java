package com.zzq.farmproductplatform.controller;

import com.zzq.farmproductplatform.common.AuthToken;
import com.zzq.farmproductplatform.common.Const;
import com.zzq.farmproductplatform.common.JWTUtils;
import com.zzq.farmproductplatform.common.ValidateCode;
import com.zzq.farmproductplatform.entity.User;
import com.zzq.farmproductplatform.jpa.UserJPA;
import com.zzq.farmproductplatform.service.UserLoginService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Controller
public class UserLoginController {

    private JWTUtils jwtUtils;

    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }


    private UserJPA userJPA;
    @Autowired
    public void setUserJPA(UserJPA userJPA) {
        this.userJPA = userJPA;
    }

    @GetMapping("/login")
    public String Login() {
        return "login";
    }


    private RedisTemplate<String, String> template;
    @Autowired
    public void setTemplate(RedisTemplate<String, String> template) {
        this.template = template;
    }

    // 生成验证码
    @GetMapping("/code")
    public void getCode(HttpServletResponse response) throws IOException {
        Map<String, Object> map = ValidateCode.generateCodeAndPic();
        String validateCode = map.get("code").toString();
        String uuid = UUID.randomUUID().toString();
        template.opsForValue().set(uuid, validateCode, Const.CAPTCHA_EXPIRE, TimeUnit.SECONDS);
        Cookie cookie = new Cookie("captcha", uuid);
        response.addCookie(cookie);
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("image/png");
        ImageIO.write((BufferedImage)map.get("codePic"), "png", os);
    }

    @GetMapping("/isDuplicated")
    @ResponseBody
    public int isDuplicated(String username) {
        return userJPA.validateUsernameDuplicated(username) >= 1 ? 1 : 0;
    }
    @PostMapping("/user/login/")
    @ResponseBody
    public String doLogin(User user, String code, @CookieValue("captcha") String realCode, HttpServletRequest request) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String redisCaptcha = template.opsForValue().get(realCode);
        System.out.println(redisCaptcha);
        if (redisCaptcha.equalsIgnoreCase(code)) {
            jsonObject.put("isValidCode", true);
            User curUser = userJPA.validateNameAndPassword(user.getUsername(), user.getPassword());
            if (curUser == null) {
                jsonObject.put("user", null);
            } else {
                String token = jwtUtils.generateToken(curUser);
                template.opsForValue().set(curUser.getUsername(), token, Const.TOKEN_EXPIRE, TimeUnit.DAYS);
                template.opsForValue().set(token, curUser.getUsername(), Const.TOKEN_EXPIRE, TimeUnit.DAYS);
                template.opsForValue().set(token + curUser.getUsername(), String.valueOf(System.currentTimeMillis()));
                jsonObject.put("user", curUser);
            }
        } else {
            jsonObject.put("isValidCode", false);
        }
        request.setAttribute("jsonObject", jsonObject);
        return "index";
    }

    @PostMapping("/test")
    @ResponseBody
    @AuthToken
    public String doTest() {
        return "asd";
    }
    @GetMapping("/TestGet")
    @ResponseBody
    @AuthToken
    public String doGetTest() {
        return "asd";
    }
    @PostMapping("/test1")
    @ResponseBody
    public String doTest1() {
        return "asd1";
    }
}
