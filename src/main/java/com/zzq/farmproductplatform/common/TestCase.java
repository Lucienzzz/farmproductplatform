package com.zzq.farmproductplatform.common;

import org.springframework.stereotype.Component;

/**
 * @author Moore
 * @since 2021/05/06
 */
@Component
public class TestCase {

    public boolean validToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("缺少必须参数：token！");
        }
        return "correct_token".equals(token);
    }
}
