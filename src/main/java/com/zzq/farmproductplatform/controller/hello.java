package com.zzq.farmproductplatform.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzq.farmproductplatform.entity.User;
import com.zzq.farmproductplatform.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
//        PageHelper.startPage(1, 5);
//        List<User> alllist = userService.alllist();
//        PageInfo<User> list = new PageInfo<>(alllist);
        return userService.alllist();
    }

    @GetMapping("/alldata")
    public PageInfo<User> aaa(@RequestParam(value = "pn", defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "ps", defaultValue = "5") Integer pageSize) {
        return userService.findAllByPage(pageNum, pageSize);
    }

    @GetMapping("/save")
    public int doSave(@RequestParam(value = "u", defaultValue = "121") String username,
                       @RequestParam(value = "p", defaultValue = "张兆强") String password) {
        return userService.save(username, password);
    }

    @PostMapping("/save1")
    public int doSave1(@RequestBody  User user) {
        return userService.save1(user);
    }
}
