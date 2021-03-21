package com.zzq.farmproductplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages="com.zzq.farmproductplatform.controller")
public class FarmproductplatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(FarmproductplatformApplication.class, args);
    }

}
