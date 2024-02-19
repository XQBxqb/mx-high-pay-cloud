package cn.high.mx.module.mission.utils;

import cn.hutool.crypto.SmUtil;

/**
 * 不可逆：从SM3产生的散列值无法直接逆向解密回原始数据。这是因为散列函数会丢失信息，使逆向运算实际上不可行。
 *
 * 唯一性：理想情况下，每个不同的输入数据都应该产生一个唯一的散列值。虽然理论上可能存在哈希碰撞（两个不同的输入生成相同的散列值），但在实际应用中这种情况极为罕见，尤其是对于像SM3这样的现代散列函数。
 *
 * 一致性：同一输入数据每次加密后产生的散列值总是相同的。
 */
public class SM3Util {
    public static String sm3(String str) {
        return SmUtil.sm3(str);
    }

    // 前端服务器登录验证的加盐
    private static final String salt = "3a41dx1d";

    public static String inputPassToFormPass(String inputPass) {
        String str = String.valueOf(salt.charAt(0)) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return sm3(str);
    }
}
