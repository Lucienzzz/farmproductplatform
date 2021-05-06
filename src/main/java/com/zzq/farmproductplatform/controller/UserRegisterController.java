package com.zzq.farmproductplatform.controller;

import com.zzq.farmproductplatform.entity.User;
import com.zzq.farmproductplatform.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserRegisterController {



    private UserJPA userJPA;
    @Autowired
    public void setUserJPA(UserJPA userJPA) {
        this.userJPA = userJPA;
    }

    private RedisTemplate<String, String> template;
    @Autowired
    public void setTemplate(RedisTemplate<String, String> template) {
        this.template = template;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @PostMapping("/user/doRegister")
    public String doRegister(@CookieValue("captcha") String cookieContent, User user, String code) {
        String redisCaptcha = template.opsForValue().get(cookieContent);
        assert redisCaptcha != null;
        if (redisCaptcha.trim().equalsIgnoreCase(code)) {
            userJPA.save(user);
            return "redirect:/login";
        }
        return "redirect:/register";
    }
}
