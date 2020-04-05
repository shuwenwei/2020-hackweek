package com.sww;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
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

    @Test
    public void testUpload() {
        Configuration cfg = new Configuration(Region.region0());
        UploadManager uploadManager = new UploadManager(cfg);
        String accessKey = "7RzINlNcl5FHQGXxws0PZCmpRZXRZWwff6oFGcS-";
        String secretKey = "jFVH-UG6gO9Y29vbfBo9-NGm5rcmshOI6GLVasFp";
        String bucket = "sww-pictures";
        String localFilePath = "/home/sww/Downloads/default.png";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        System.out.println(upToken);
        String key = "default";
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }
}
