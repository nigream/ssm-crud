package com.nigream.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Nigream
 * @date 2021年3月10日 下午4:35:41
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:springmvc.xml"})
@WebAppConfiguration
public class MVCTest {
	
	// 传入SpringMvc的ioc
	@Autowired
	WebApplicationContext context;
	
	//虚拟MVC请求
	MockMvc mockMvc;
	
	@Before
	public void initMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testPage() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employee/findAll").param("pageNum", "2")).andReturn();
		System.out.println(result.getRequest().getAttribute("pageInfo"));
		MockHttpServletRequest request = result.getRequest();
		MockHttpServletResponse response = result.getResponse();
		System.out.println(result.getRequest());
		System.out.println(result.getResponse().getOutputStream());
	}
	
	
}
