package com.zzq.farmproductplatform.controller;

import com.zzq.farmproductplatform.common.Const;
import com.zzq.farmproductplatform.common.Result;
import com.zzq.farmproductplatform.entity.User;
import com.zzq.farmproductplatform.jpa.ProductBigCategory;
import com.zzq.farmproductplatform.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserJPA userJPA;

    @Autowired
    public void setUserJPA(UserJPA userJPA) {
        this.userJPA = userJPA;
    }

    @PostMapping("/login")
    @ResponseBody
    public Result doLogin(User user, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        User curUser = userJPA.validateNameAndPassword(user.getUsername(), user.getPassword());
        if (curUser == null) {
            return Result.error(null, Const.USERNAME_PASSWORD_ERROR, Const.ERROR);
        } else {
            Cookie cookie = new Cookie("userUsername", user.getUsername());
            cookie.setMaxAge(Const.SEVEN_DAY);
            cookie.setPath("/");
            response.addCookie(cookie);
            return Result.ok(user);
        }
    }

    @PostMapping("/queryAllBigCategory")
    @ResponseBody
    public Result queryAllBigCategory() {
        List<Map<String, Object>> map = userJPA.selectBigCategory();
        System.out.println(map);
        return Result.ok(map);
    }

    @PostMapping("/queryAllSmallCategory")
    @ResponseBody
    public Result queryAllSmallCategory() {
        List<Map<String, Object>> map = userJPA.selectSmallCategory();
        System.out.println(map);
        return Result.ok(map);
    }

    @PostMapping("/getUserInfo")
    @ResponseBody
    public Result getUserInfo() {
        List<Map<String, Object>> map = userJPA.getUserInfo();
//        System.out.println(map);
        return Result.ok(map);
    }
}
