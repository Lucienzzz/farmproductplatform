package com.zzq.farmproductplatform.service;

import com.zzq.farmproductplatform.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserLoginService {
    int loginCounts(User user);
}
