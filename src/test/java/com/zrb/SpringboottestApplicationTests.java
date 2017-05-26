package com.zrb;

import com.zrb.Mapper.CourseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
public class SpringboottestApplicationTests {
	@Resource
	private CourseMapper courseMapper;
	@Test
	public void contextLoads() {
	}

}
