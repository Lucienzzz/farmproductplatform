package com.zzq.farmproductplatform.mapper;

import com.zzq.farmproductplatform.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    @Select("select * from f_user_id")
    List<User> allList();
}
