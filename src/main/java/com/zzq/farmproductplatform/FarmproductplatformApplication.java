package com.zzq.farmproductplatform;

import com.zzq.farmproductplatform.common.JWTUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.zzq.farmproductplatform.repository")
@SpringBootApplication(scanBasePackages="com.zzq.farmproductplatform")
public class FarmproductplatformApplication {

    @Test
    public void a() {
        JWTUtils jwtUtils = new JWTUtils();
        String asd = jwtUtils.generateToken("asdasd");
        System.out.println(asd);
    }

    public static void main(String[] args) {
        SpringApplication.run(FarmproductplatformApplication.class, args);
    }

}
