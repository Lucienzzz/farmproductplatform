package com.zzq.farmproductplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.zzq.farmproductplatform.mapper")
@SpringBootApplication(scanBasePackages="com.zzq.farmproductplatform")
public class FarmproductplatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(FarmproductplatformApplication.class, args);
    }

}
