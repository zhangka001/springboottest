package com.zrb;

import com.zrb.Mapper.CourseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


import javax.annotation.Resource;


/**
 * Created by zrb on 2017/5/20.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:application.yml")
public class Test01 {
    @Resource
    private CourseMapper courseMapper;


    @Test
    public void test01(){
        for (Integer i = 0 ; i < 100 ; i++ ) {
            courseMapper.Insert(i, i.toString());
        }
    }
}
