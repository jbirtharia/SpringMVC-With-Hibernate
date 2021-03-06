package com.demo.springmvc.security;


import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class UserContext extends User {
	
	private static final long serialVersionUID = 7638163377962847713L;

	private long empid;
	
	private String name;
	
	private Double sal;
	
	private String username;
	
	private String pass;
	
	private String phoneno;
	
	private String otp;
	
	private String role;
	
	private boolean status;

	
	public UserContext(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) 
	{
		//Calling super constructor for setting properties
		super(username, password, enabled,accountNonExpired,credentialsNonExpired,accountNonLocked,authorities);
	}

	
	public long getEmpid() {
		return empid;
	}



	public void setEmpid(long empid) {
		this.empid = empid;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Double getSal() {
		return sal;
	}



	public void setSal(Double sal) {
		this.sal = sal;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPass() {
		return pass;
	}



	public void setPass(String pass) {
		this.pass = pass;
	}



	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	


	public String getPhoneno() {
		return phoneno;
	}


	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}


	public String getOtp() {
		return otp;
	}


	public void setOtp(String otp) {
		this.otp = otp;
	}


	@Override
	public String toString() {
		return "UserContext [empid=" + empid + ", name=" + name + ", sal=" + sal + ", username=" + username + ", pass="
				+ pass + ", phoneno=" + phoneno + ", otp=" + otp + ", role=" + role + ", status=" + status + "]";
	}


	

}
