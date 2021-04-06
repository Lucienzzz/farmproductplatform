package com.zzq.farmproductplatform.controller;

import com.github.pagehelper.PageInfo;
import com.zzq.farmproductplatform.common.JsonResult;
import com.zzq.farmproductplatform.entity.User;
import com.zzq.farmproductplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Hello {
    @Autowired
    private UserService userService;

    @RequestMapping("hello")
    public String a() {
        return "ad";
    }

    @GetMapping("/data")
    public List<User> aa() {
//        PageHelper.startPage(1, 5);
//        List<User> allList = userService.allList();
//        PageInfo<User> list = new PageInfo<>(allList);
        return userService.allList();
    }

    @GetMapping("/alldata")
    public JsonResult<PageInfo<User>> aaa(@RequestParam(value = "pn", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "ps", defaultValue = "5") Integer pageSize) {
        return JsonResult.ok(userService.findAllByPage(pageNum, pageSize));
    }

    @GetMapping("/save")
    public JsonResult<Object> doSave(@RequestParam(value = "u", defaultValue = "121") String username,
                                     @RequestParam(value = "p", defaultValue = "张兆强") String password) {
        userService.save(username, password);
        return JsonResult.ok();
    }

    @PostMapping("/save1")
    public JsonResult<Object> doSave1(@RequestBody User user) {
        userService.save1(user);
        return JsonResult.ok();
    }
}
