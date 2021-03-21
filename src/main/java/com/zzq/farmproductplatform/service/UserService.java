package com.zzq.farmproductplatform.service;

import com.zzq.farmproductplatform.entity.User;
import com.zzq.farmproductplatform.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper mapper;
    public List<User> alllist() {
        return mapper.allList();
    }
}
