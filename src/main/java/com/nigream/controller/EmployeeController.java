package com.nigream.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nigream.dao.EmployeeMapper;
import com.nigream.entity.Employee;
import com.nigream.entity.Message;
import com.nigream.service.EmployeeService;

/**
 * @author Nigream
 * @date 2021年3月8日 下午4:28:45
 * 
 */
@Controller
@RequestMapping("/employee")
@ResponseBody
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
//	@RequestMapping("/findAll")
//	public PageInfo<Employee> findAll(@RequestParam(value="pn",defaultValue = "1") Integer pn) {
//		PageHelper.startPage(pn ,5);
//		List<Employee> employees = employeeService.findAll();
//		PageInfo<Employee> pageInfo = new PageInfo<Employee>(employees,5);
//		return pageInfo;
//	}
	
	@RequestMapping("/findAll")
	public Message findAll(@RequestParam(value="pn",defaultValue = "1") Integer pn) {
		
		PageHelper.startPage(pn ,5);
		List<Employee> employees = employeeService.findAll();
		PageInfo<Employee> pageInfo = new PageInfo<Employee>(employees,5);
		return Message.success().add("pageInfo",pageInfo);
	}
	
	/**
	 * post-保存 get-查询 put-更新 delete-删除 
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	public Message saveOne(@Valid Employee employee,BindingResult result) {
		Map<String, String> fieldErrorMap = new HashMap<>();
		List<FieldError> fieldErrors = result.getFieldErrors();
		if(result.hasErrors()) {
			for(FieldError fieldError : fieldErrors) {
				System.out.println("错误的字段名：" + fieldError.getField());
				System.out.println("错误信息：" + fieldError.getDefaultMessage());
				fieldErrorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			if(!(Boolean)checkEmpName(employee.getEmpName()).getDataMap().get("isUnique")) {
				return Message.fail().add("fieldErrorMap", fieldErrorMap).add("isUnique", false);
			}
			return Message.fail().add("fieldErrorMap", fieldErrorMap).add("isUnique", true);
		}
		
		if(!(Boolean)checkEmpName(employee.getEmpName()).getDataMap().get("isUnique")) {
			return Message.fail().add("fieldErrorMap", fieldErrorMap).add("isUnique", false);
		}
		
		employeeService.saveOne(employee);
		return Message.success();
	}
	
	@RequestMapping("/checkEmpName")
	public Message checkEmpName(String empName) {		
		Boolean isUnique = employeeService.checkEmpName(empName);
		if(isUnique) {
			return Message.success().add("isUnique", isUnique);
		} else {
			return Message.fail().add("isUnique", isUnique);
		}
	}
	
	@RequestMapping(value = "/emp/{empId}", method = RequestMethod.GET)
	public Message findbyId(@PathVariable("empId") Integer empId) {	
		Employee employee = employeeService.findById(empId);
		if(employee != null) {
			return Message.success().add("employee", employee);
		} else {
			return Message.fail().add("employee", employee);
		}
	}
	
	@RequestMapping(value = "/findbyIdWithDept")
	public Message findbyIdWithDept(Integer empId) {	
		Employee employee = employeeService.findByIdWithDept(empId);
		if(employee != null) {
			return Message.success().add("employee", employee);
		} else {
			return Message.fail().add("employee", employee);
		}
	}
	
	@RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
	public Message updateById(@Valid Employee employee, BindingResult result) {	
		Map<String, String> fieldErrorMap = new HashMap<>();
		List<FieldError> fieldErrors = result.getFieldErrors();
		if(result.hasErrors()) {
			for(FieldError fieldError : fieldErrors) {
				System.out.println("错误的字段名：" + fieldError.getField());
				System.out.println("错误信息：" + fieldError.getDefaultMessage());
				fieldErrorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Message.fail().add("fieldErrorMap", fieldErrorMap);
		}
		/* System.out.println(employee); */
		employeeService.updateById(employee);
		return Message.success();
	}
	
	@RequestMapping(value = "/emp/{empId}", method = RequestMethod.DELETE)
	public Message deleteById(@PathVariable("empId") Integer empId) {	
		employeeService.deleteById(empId);
		return Message.success();
	}
	
	@RequestMapping(value = "/delChecked", method = RequestMethod.DELETE)
	public Message deleteByIds(String checkedIds) {
		if(checkedIds.contains(",")) {
			String[] idArr = checkedIds.split(",");
			List<Integer> ids = new ArrayList<Integer>();
			for(String id : idArr) {
				ids.add(Integer.parseInt(id));
			}
			employeeService.deleteByIds(ids);
		} else {
			employeeService.deleteById(Integer.parseInt(checkedIds));
		}
		return Message.success();
	}
}
