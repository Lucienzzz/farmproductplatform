package com.zzq.farmproductplatform.jpa;

import com.zzq.farmproductplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface UserJPA extends JpaRepository<User, Long> {
    @Query(value = "select * from usercheck where user_username = ? and user_password = ?", nativeQuery = true)
    User validateNameAndPassword(String username, String password);
    @Query(value = "select count(*) from user where username = ?", nativeQuery = true)
    int validateUsernameDuplicated(String username);

    @Query(value = "select * from productbigcategory", nativeQuery = true)
    List<Map<String, Object>> selectBigCategory();

    @Query(value = "select product_category_id, product_category_name from productcategory", nativeQuery = true)
    List<Map<String, Object>> selectSmallCategory();

    @Query(value = "select user_id, user_name, user_email " +
            "from userinfo", nativeQuery = true)
    List<Map<String, Object>> getUserInfo();
}
