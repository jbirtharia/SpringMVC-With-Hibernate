package com.demo.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.springmvc.dao.EmployeeDAO;
import com.demo.springmvc.model.Employee;
import com.demo.springmvc.utility.EmailUtility;
import com.demo.springmvc.utility.SMSClass;
import com.demo.springmvc.utility.SecurityUtility;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeDAO employeeDAO;
	

	public List<Employee> AllEmployee() {
		return employeeDAO.GetAllEmployee();
	}

	public void SaveEmployee(Employee employee) {
		employeeDAO.Save(employee);
		try {
			
			EmailUtility.MailSend(employee.getUsername(),employee.getPass());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Employee findById(long empid) {
		return employeeDAO.FindEmpId(empid);
	}

	public void UpdateEmployee(Employee employee) {
		employeeDAO.Update(employee);
		
	}

	public String deleteUserByID(long empid) {
		return employeeDAO.Delete(empid);
		
	}

	public Employee findByName(String name) {
		return employeeDAO.FindEmpName(name);
	}
	
	public void SaveVerification(String name) {
		Employee employee = null;
		try {
			employee = employeeDAO.FindEmpName(SecurityUtility.Decrypt(name));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		employee.setStatus(true);
		employeeDAO.Save(employee);	
	}

	public void SavePassword(Employee employee) {
		Employee newemployee=employeeDAO.FindEmpName(employee.getUsername());
		newemployee.setPass(employee.getPass());
		employeeDAO.Save(newemployee);		
	}

	public void SendMessage(String number) {
		SMSClass.SMS(SMSClass.OTP(4),number);
		
	}


}
