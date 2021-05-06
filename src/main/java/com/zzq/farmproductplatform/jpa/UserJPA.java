package com.zzq.farmproductplatform.jpa;

import com.zzq.farmproductplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserJPA extends JpaRepository<User, Long> {
    @Query(value = "select * from user where username = ? and password = ?", nativeQuery = true)
    User validateNameAndPassword(String username, String password);
    @Query(value = "select count(*) from user where username = ?", nativeQuery = true)
    int validateUsernameDuplicated(String username);
}
