package com.zzq.farmproductplatform;

import com.zzq.farmproductplatform.dao.UserDao;
import com.zzq.farmproductplatform.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

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

}
