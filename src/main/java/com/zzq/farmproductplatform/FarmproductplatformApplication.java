package com.zzq.farmproductplatform;

import com.zzq.farmproductplatform.common.JWTUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.zzq.farmproductplatform.repository")
@SpringBootApplication(scanBasePackages="com.zzq.farmproductplatform")
public class FarmproductplatformApplication {


    @Test
    public void a() {
        JWTUtils jwtUtils = new JWTUtils();
        Claims claims = jwtUtils.parseToken("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJsdWN5IiwiZXhwIjoxNjE5NTgyMjYwLCJpYXQiOjE2MTk1ODE2NjAsInJvbGVzIjoiYWRtaW4ifQ.tZ_OcxpR3OK2AdKra1UunyGDYtrs-Lp3MG05e6iNE7Q");
        System.out.println(claims);

    }

    public static void main(String[] args) {
        SpringApplication.run(FarmproductplatformApplication.class, args);
    }

}
