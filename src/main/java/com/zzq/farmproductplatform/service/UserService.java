package com.zzq.farmproductplatform.service;

import com.github.pagehelper.PageInfo;
import com.zzq.farmproductplatform.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
//    @Autowired
//    private UserDao mapper;
//    public List<User> alllist() {
//        return mapper.allList();
//    }

    List<User> alllist();

    PageInfo<User> findAllByPage(int pageNum, int pageSize);

    int save(String username, String password);

    int save1(User user);
}
