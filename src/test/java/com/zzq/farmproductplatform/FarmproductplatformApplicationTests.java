package com.zzq.farmproductplatform;

import com.zzq.farmproductplatform.common.JWTUtils;
import com.zzq.farmproductplatform.repository.UserDao;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FarmproductplatformApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
//        User user = new User();
//        user.setId(null);
//        user.setPassword("asd");
//        user.setUsername("qwe");
//        int i = userDao.save1(user);
//        System.out.println(i);
//
//        Jedis jedis = new Jedis("192.168.2.128", 6379);
//        System.out.println(jedis.ping());
    }
    @Test
    void a() {
//        JWTUtils jwtUtils = new JWTUtils();
//        Claims claims = jwtUtils.parseToken("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJsdWN5IiwiZXhwIjoxNjE5NTgyMjYwLCJpYXQiOjE2MTk1ODE2NjAsInJvbGVzIjoiYWRtaW4ifQ.tZ_OcxpR3OK2AdKra1UunyGDYtrs-Lp3MG05e6iNE7Q");
//        System.out.println(claims);
//
//        Jedis jedis = new Jedis("redis.yuhangma.com");
//        jedis.auth("redisniub");
//        System.out.println(jedis.ping());
//        jedis.set("asd", "asd");
//        System.out.println("V9ZS".equalsIgnoreCase("v9zs"));
    }

}
