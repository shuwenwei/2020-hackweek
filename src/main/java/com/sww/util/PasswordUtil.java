package com.sww.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author sww
 */
public class PasswordUtil {

    private static final String SALT = "ah13qf11399fa013a";

    private PasswordUtil(){
    }

    public static String generate(String password){
        String newPassword = DigestUtils.md5Hex(password);
        return DigestUtils.md5Hex(password + SALT);
    }

    public static boolean verify(String password, String md5){
        return md5.equals(generate(password));
    }

}