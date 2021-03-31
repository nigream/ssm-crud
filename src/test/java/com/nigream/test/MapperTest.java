package com.nigream.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nigream.dao.DepartmentMapper;
import com.nigream.dao.EmployeeMapper;
import com.nigream.entity.Employee;

/**
 * 
 * @author Nigream
 * @date 2021年3月7日 上午11:51:58
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MapperTest {
	
	@Autowired
	DepartmentMapper departmentMapper;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	SqlSession sqlSession;
	
	/**
	 * 测试DepartmentMapper
	 */
	@Test
	public void testCrud() {
		
//		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//		DepartmentMapper departmentMapper = classPathXmlApplicationContext.getBean(DepartmentMapper.class);
		System.out.println(departmentMapper);
		
		//1、插入部门数据
//		departmentMapper.insertSelective(new Department(null,"开发部"));
//		departmentMapper.insertSelective(new Department(null,"测试部"));
		
		//2、插入员工数据
		employeeMapper.insertSelective(new Employee(null,"Nigream","M","Nigream@gmail.com",1));
		//3、批量插入多个员工
//		for(int i = 0; i <= 10000;i++) {
//			employeeMapper.insertSelective(new Employee(null,"Nigream","M","Nigream@gmail.com",1));
//		}
		
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for(int i = 0; i < 1000; i++) {
			String uuid = UUID.randomUUID().toString().substring(0,5)+i;
			mapper.insertSelective(new Employee(null,uuid,"M",uuid+"@gmail.com",1));
		}
	}	
	
	
	@Test
	public void test01() {
		System.out.println("hello");
	}
}
