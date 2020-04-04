package com.sww;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest(classes = MySpringBootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MySpringBootApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

//    @Autowired
//    private UserService userService;

    @Test
    public void test() {
        System.out.println("aeafaef");
        redisTemplate.opsForValue().set("test", "t");
        System.out.println(redisTemplate.opsForValue().get("test"));
//        System.out.println(userService);
//        User user = new User();
//        user.setId(1111111L);
//        user.setUsername("afeaef");
//        user.setEmail("aefaef");
//        System.out.println(redisTemplate);
//        redisTemplate.opsForValue().set("user1", user);
//        System.out.println(redisTemplate.opsForValue().get("user1"));

    }
}
