package org.zzq.farmproductplatform.model.dao;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.zzq.farmproductplatform.common.utils.MyMapper;
import org.zzq.farmproductplatform.model.entity.User;
@Component
public interface UserMapper extends MyMapper<User> {

    @Select("select * from user where username = #{username}")
    @Results({
            @Result(id = true, property = "userId", column = "user_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "zipCode", column = "zip_code"),
            @Result(property = "location", column = "location"),
            @Result(property = "detailAddress", column = "detail_address"),
            @Result(property = "identity", column = "identity"),
            @Result(property = "active", column = "active"),
            @Result(property = "code", column = "code"),
            @Result(property = "updated", column = "updated"),
            @Result(property = "created", column = "created"),
    })
    User selectUser(String username);
}