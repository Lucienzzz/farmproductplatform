package com.zzq.farmproductplatform.service.impl;

import com.zzq.farmproductplatform.repository.UserLoginDao;
import com.zzq.farmproductplatform.entity.User;
import com.zzq.farmproductplatform.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserLoginDao userLoginDao;

    @Override
    public int loginCounts(String username, String password) {
        return userLoginDao.loginCounts(username, password);
    }
}
