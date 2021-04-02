package com.nigream.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nigream.dao.EmployeeMapper;
import com.nigream.entity.Employee;
import com.nigream.entity.EmployeeExample;
import com.nigream.entity.EmployeeExample.Criteria;
import com.nigream.service.EmployeeService;

/**
 * @author Nigream
 * @date 2021年3月10日 下午12:19:59
 * 
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeMapper employeeMapper;
	
	@Override
	public List<Employee> findAll() {
		return employeeMapper.selectByExampleWithDept(null);
	}

	@Override
	public void saveOne(Employee employee) {
		employeeMapper.insertSelective(employee);
	}

	/**
	 * true empName可用   false empName不可用
	 */
	@Override
	public Boolean checkEmpName(String empName) {
		EmployeeExample employeeExample = new EmployeeExample();
		Criteria criteria = employeeExample.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		return employeeMapper.countByExample(employeeExample) == 0;
	}

	@Override
	public Employee findById(Integer empId) {
		return employeeMapper.selectByPrimaryKey(empId);
	}

	@Override
	public void updateById(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}

	@Override
	public void deleteById(Integer empId) {
		employeeMapper.deleteByPrimaryKey(empId);
	}

	@Override
	public Employee findByIdWithDept(Integer empId) {
		return employeeMapper.selectByPrimaryKeyWithDept(empId);
	}

	@Override
	public void deleteByIds(List<Integer> ids) {
		EmployeeExample employeeExample = new EmployeeExample();
		employeeExample.createCriteria().andEmpIdIn(ids);
		employeeMapper.deleteByExample(employeeExample);
		
	}

}
