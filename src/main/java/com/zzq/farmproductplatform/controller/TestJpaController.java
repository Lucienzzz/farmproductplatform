package com.zzq.farmproductplatform.controller;

import com.zzq.farmproductplatform.common.JsonResult;
import com.zzq.farmproductplatform.jpa.model.JpaUser;
import com.zzq.farmproductplatform.jpa.repository.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Moore
 * @since 2021/04/12
 */
@RestController
public class TestJpaController {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @GetMapping("/test/jpa")
    public JsonResult<JpaUser> findByUsername(String username) {
        return JsonResult.ok(jpaUserRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("用户不存在")));
    }
}
