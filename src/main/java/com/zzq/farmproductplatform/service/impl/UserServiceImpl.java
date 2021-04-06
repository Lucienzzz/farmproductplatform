package com.zzq.farmproductplatform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzq.farmproductplatform.repository.UserDao;
import com.zzq.farmproductplatform.entity.User;
import com.zzq.farmproductplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> allList() {
        PageHelper.startPage(1, 5);
        List<User> users = userDao.allList();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo.getList();
    }

    @Override
    public PageInfo<User> findAllByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userDao.allList();
        return new PageInfo<>(users);
    }

    @Override
    public int save(String username, String password) {
        return userDao.save(username, password);
    }

    @Override
    public int save1(User user) {
        return userDao.save1(user);
    }
}
