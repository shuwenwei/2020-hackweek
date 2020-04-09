package com.sww;

import com.sww.service.UserInfoService;
import com.sww.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MyTest1 {

    private RedisUtil redisUtil;
    private UserInfoService userInfoService;

    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Test
    public void test1() {
//        redisUtil.like(11111L, 1247456332991426561L);
//        System.out.println(redisUtil.isLiked(11111L, 1247456332991426561L));
//        System.out.println(redisUtil.getLikedNum(11111L));
//        redisUtil.unlike(11111L, 1247456332991426561L);
//        System.out.println(redisUtil.isLiked(11111L, 1247456332991426561L));
//        System.out.println(redisUtil.getLikedNum(11111L));
//        Set<Object> set = redisUtil.getMostLiked();
//        for (Object id : set) {
//            System.out.println( (Long) id);
//        }
        redisUtil.followUser(1111L, 2222L);
        redisUtil.followUser(1111L, 3333L);
        redisUtil.followUser(2222L, 3333L);
//        redisUtil.unfollowUser(1111L, 3333L);
        System.out.println("1111 -> " + redisUtil.getFollowNum(1111L));
        System.out.println("2222 -> " + redisUtil.getFollowNum(2222L));
        System.out.println("3333 -> " + redisUtil.getFollowNum(3333L));
        System.out.println("3333 -> " + redisUtil.getFollowedNum(3333L));

        Set<Object> followers = redisUtil.getFollowers(3333L);
        Set<Object> followes = redisUtil.getFollows(1111L);
        System.out.println(followes);
        userInfoService.getViewListUsers(followers).forEach(System.out::println);
    }
}
