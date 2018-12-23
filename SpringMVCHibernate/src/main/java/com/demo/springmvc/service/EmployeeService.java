package com.demo.springmvc.service;

import java.util.List;

import com.demo.springmvc.model.Employee;

public interface EmployeeService {

	List<Employee> AllEmployee();
	void SaveEmployee(Employee employee);
	Employee findById(long empid);
	void SaveVerification(String name);
	void UpdateEmployee(Employee emp);
	String deleteUserByID(long empid);
	Employee findByName(String name);
	void SavePassword(Employee employee);
	void SendMessage(String number);
}
