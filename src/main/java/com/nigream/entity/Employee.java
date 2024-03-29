package com.nigream.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class Employee {
    private Integer empId;

    @Pattern(regexp = "(^[a-zA-Z0-9\\u2E80-\\u9FFF_-]{2,16}$)", message = "必须为2-16位【大小写字母、数字、中文汉字、_、-】的组合")
    private String empName;

    private String gender;

//    @Email
    @Pattern(regexp = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$", message = "邮箱格式不正确！")
    private String email;

    private Integer dId;
    
    // 查询员工时，部门信息也是查询好的
    private Department department;
    
    /**
	 * 
	 */
	public Employee() {
		super();
	}

	/**
	 * @param empId
	 * @param empName
	 * @param gender
	 * @param email
	 * @param dId
	 * @param department
	 */
	public Employee(Integer empId, String empName, String gender, String email, Integer dId) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.gender = gender;
		this.email = email;
		this.dId = dId;
	}

    public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getdId() {
        return dId;
    }

    @Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", gender=" + gender + ", email=" + email
				+ ", dId=" + dId + ", department=" + department + "]";
	}

	public void setdId(Integer dId) {
        this.dId = dId;
    }
}