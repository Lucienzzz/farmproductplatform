package com.zzq.farmproductplatform.repository;

import com.zzq.farmproductplatform.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao {
    @Select("select * from f_user_id")
    List<User> allList();

    @Insert("insert into f_user_id values(null, #{username}, #{password})")
    int save(String username, String password);

    @Insert("insert into f_user_id values(null, #{username}, #{password})")
    int save1(User user);

    @Select("select count(1) from f_user_id where username = #{username} and `password` = {#password}")
    int loginCounts(User user);
}
