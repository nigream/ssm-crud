package com.nigream.service;

import java.util.List;

import com.nigream.entity.Department;

/**
 * @author Nigream
 * @date 2021年3月28日 下午3:52:52
 * 
 */
public interface DepartmentService {
	
	List<Department> findAll();
	
}
