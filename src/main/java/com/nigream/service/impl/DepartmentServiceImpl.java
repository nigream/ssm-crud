package com.nigream.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nigream.dao.DepartmentMapper;
import com.nigream.entity.Department;
import com.nigream.service.DepartmentService;

/**
 * @author Nigream
 * @date 2021年3月28日 下午3:53:46
 * 
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	DepartmentMapper departmentMapper;

	@Override
	public List<Department> findAll() {
		return departmentMapper.selectByExample(null);
	}
	
	

}
