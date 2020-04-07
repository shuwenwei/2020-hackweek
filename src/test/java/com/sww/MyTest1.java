package com.sww;

import com.sww.util.ValidateCodeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MyTest1 {

    @Test
    public void test1() {
        ValidateCodeUtil.sendMessage("aaa", "13979062948@163.com");
    }
}
