package com.zzq.farmproductplatform.common;

import com.zzq.farmproductplatform.FarmproductplatformApplicationTests;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Moore
 * @since 2021/05/06
 */
public class TestCaseTest extends FarmproductplatformApplicationTests {

    @Autowired
    private TestCase testCase;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testValidToken() {
        boolean result = testCase.validToken("correct_token");
        Assertions.assertTrue(result, "错误的 token");
    }

    @Test
    public void testValidToken_noToken() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("缺少必须参数：token！");
        testCase.validToken(null);
    }

    @Test
    public void testValidToken_errToken() {
        boolean result = testCase.validToken("errToken");
        Assertions.assertFalse(result);
    }
}