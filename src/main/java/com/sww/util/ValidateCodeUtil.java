package com.sww.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author sww
 */
@Component
public class ValidateCodeUtil {

    @SuppressWarnings("SpellCheckingInspection")
    private static String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static JavaMailSender javaMailSender;
    private static RedisUtil redisUtil;
    private static Random random = new Random();

    private ValidateCodeUtil() {
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        ValidateCodeUtil.redisUtil = redisUtil;
    }

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        ValidateCodeUtil.javaMailSender = javaMailSender;
    }

    private static String generateValidateCode() {
        StringBuilder stringBuilder = new StringBuilder();
        int length = 6;
        for (int i = 0; i < length; i++) {
            stringBuilder.append(randString.charAt(random.nextInt(35)));
        }
        return stringBuilder.toString();
    }

    /**
     * @param subject 主题
     * @param to 收件人的邮箱
     * @return
     */
    public static void sendMessage(String subject, String to) {
        System.out.println(javaMailSender == null);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setTo(to);
        message.setFrom("13979062948@163.com");
        String validateCode = generateValidateCode();
        String text = "validateCode : " + validateCode;
        redisUtil.set("validateCode::" + to, validateCode,60 * 10);
        message.setText(text);
        javaMailSender.send(message);
    }

    /**
     * @param emailAddress 邮箱地址
     * @param userValidateCode 用户输入的验证码
     * @return 是否成功
     */
    public static boolean checkValidateCode(String emailAddress, String userValidateCode) {
        String validateCode = (String) redisUtil.get("validateCode::" + emailAddress);
        return (validateCode != null) && validateCode.equals(userValidateCode);
    }

}
