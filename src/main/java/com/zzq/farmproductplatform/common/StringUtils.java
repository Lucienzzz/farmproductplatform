package com.zzq.farmproductplatform.common;

public class StringUtils {
    public static boolean isEmpty(String... strings) {
        return notEmpty(strings);
    }

    public static boolean notEmpty(String... strings) {
        for (String s : strings) {
            if (s == null || s.equals("")) {
                return false;
            }
        }
        return true;
    }
}
