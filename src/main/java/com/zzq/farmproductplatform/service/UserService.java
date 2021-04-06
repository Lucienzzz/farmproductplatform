package com.zzq.farmproductplatform.service;

import com.github.pagehelper.PageInfo;
import com.zzq.farmproductplatform.entity.User;

import java.util.List;

public interface UserService {

    List<User> allList();

    PageInfo<User> findAllByPage(int pageNum, int pageSize);

    int save(String username, String password);

    int save1(User user);
}
