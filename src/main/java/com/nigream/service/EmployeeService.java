package com.nigream.service;

import java.util.List;

import com.nigream.entity.Employee;

/**
 * @author Nigream
 * @date 2021年3月10日 下午12:18:34
 * 
 */
public interface EmployeeService {
	List<Employee> findAll();
	void saveOne(Employee employee);
	Boolean checkEmpName(String empName);
	Employee findById(Integer empId); 
	Employee findByIdWithDept(Integer empId); 
	void updateById(Employee employee);
	void deleteById(Integer empId);
	void deleteByIds(List<Integer> ids);
}
