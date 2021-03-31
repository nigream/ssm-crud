package com.nigream.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nigream.entity.Department;
import com.nigream.entity.Message;
import com.nigream.service.DepartmentService;

/**
 * @author Nigream
 * @date 2021年3月28日 下午3:51:43
 * 
 */
@Controller
@RequestMapping("/department")
@ResponseBody
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentService;
	
	
	@RequestMapping("/findAll")
	public Message findAll() {
		List<Department> departments = departmentService.findAll();
		return Message.success().add("departments", departments);
	}
	
}
