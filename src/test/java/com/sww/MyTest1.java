package com.sww;

import com.sww.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MyTest1 {

    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Test
    public void test1() {
        redisUtil.like(11111L, 1247456332991426561L);
        System.out.println(redisUtil.getLikedNum(11111L));
        redisUtil.unlike(11111L, 1247456332991426561L);
        System.out.println(redisUtil.getLikedNum(11111L));
        Set<Object> set = redisUtil.getMostLiked();
        for (Object id : set) {
            System.out.println( (Long) id);
        }

    }
}
