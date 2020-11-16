package com.wangchuan;

import com.wangchuan.config.EmployeeConfig;
import com.wangchuan.config.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * springboot 单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDemoApplicationTests {

	@Autowired
	private Person person;

	@Autowired
	private EmployeeConfig employeeConfig;

	@Test
	public void contextLoads() {
		System.out.println(person);
		System.out.println(employeeConfig);
	}

}
