package com.zzq.farmproductplatform.service.impl;

import com.zzq.farmproductplatform.dao.UserLoginDao;
import com.zzq.farmproductplatform.service.UserLoginService;
import com.zzq.farmproductplatform.service.UserService;

public class UserLoginServiceImpl implements UserLoginService {

    private UserLoginDao userLoginDao;

    @Override
    public int loginCounts(String username, String password) {
        return userLoginDao.loginCounts(username, password);
    }
}
