package com.qifeng.will;

import com.qifeng.will.es.util.EslaticSearchUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ESApplicationTests {

    @Test
    public void contextLoads() {

        new EslaticSearchUtil().generateUpdateByQuery();


    }

}
