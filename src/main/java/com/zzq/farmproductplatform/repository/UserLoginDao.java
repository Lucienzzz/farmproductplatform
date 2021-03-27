package com.zzq.farmproductplatform.repository;

import com.zzq.farmproductplatform.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserLoginDao {
    @Select("select count(1) from f_user_id where username = #{username} and `password` = {#password}")
    int loginCounts(User user);
}
