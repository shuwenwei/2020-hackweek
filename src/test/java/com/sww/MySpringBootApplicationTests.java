package com.sww;


import com.sww.pojo.User;
import com.sww.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest(classes = MySpringBootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MySpringBootApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserService userService;

    @Test
    public void test() {
        User user = new User();
        user.setRole("user");
        user.setUsername("user3");
        userService.save(user);
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
