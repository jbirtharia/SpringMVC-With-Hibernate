package com.demo.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.springmvc.model.Employee;
import com.demo.springmvc.service.EmployeeService;
import com.demo.springmvc.utility.EmailUtility;
import com.demo.springmvc.utility.SecurityUtility;
import com.demo.springmvc.utility.UserContextFactory;


@Controller
public class AppController {

	private static final Logger LOGGER = Logger.getLogger(AppController.class.getName());

	@Autowired
	EmployeeService employeeService;


	@RequestMapping(value = {"/list" }, method = RequestMethod.GET)
	public String ListUsers(Model model,final RedirectAttributes redirectAttributes) {
		
		if(UserContextFactory.getUserContext().getUsername()!=null && UserContextFactory.getUserContext().isStatus())
		{
			model.addAttribute("empObj",employeeService.AllEmployee());
			model.addAttribute("loggedinuser", UserContextFactory.getUserContext().getName());
			return "userslist";
		}
		else
		{
			redirectAttributes.addFlashAttribute("ver","You must to verify yourself.Please check your Mail-ID.");
			return "redirect:/loginpage";

		}
	}

	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
	public String NewUser(ModelMap model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("loggedinuser", UserContextFactory.getUserContext().getName());
		model.addAttribute("edit", false);
		return "registration";
	}

	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
	public ModelAndView SaveUser(@Valid Employee employee, BindingResult result,
			ModelMap model) {

		
		ModelAndView mav =new ModelAndView("registration");
		if (result.hasErrors()) {
			return mav;
		}
		if(!employee.getPass().equals(employee.getConfirmPass()))
		{
			System.out.println("In not equal password");
			mav.addObject("mssg","Password and confirm password must be same.");
			return mav;
		}
		if(UserContextFactory.getUserContext().getOtp()==null)
		{
			mav.addObject("otp","Please verify your mobile.");
			return mav;
		}
		employeeService.SaveEmployee(employee); 
		model.addAttribute("success", "User " + employee.getName() + " registered successfully");
		mav.setViewName("registrationsuccess");
		return mav;
	}


	@RequestMapping(value = {"/" ,"/loginpage" }, method = RequestMethod.GET)
	public String NewUser(Model model) {
		if(!model.containsAttribute("employeeBean"))
			model.addAttribute("employeeBean",new Employee());

		return "login";
	}


	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String LogoutPage (HttpServletRequest request, HttpServletResponse response){
		UserContextFactory.getUserContext().setUsername(null);
		UserContextFactory.getUserContext().setName(null);
		UserContextFactory.getUserContext().setOtp(null);
		LOGGER.info("UserContext {} : "+UserContextFactory.getUserContext());
		return "redirect:/loginpage?logout";
	}


	@RequestMapping(value = { "/edit-user-{empid}" }, method = RequestMethod.GET)
	public String EditUser(@PathVariable long empid, ModelMap model) {
		Employee employee = employeeService.findById(empid);
		LOGGER.info("Employee : "+employee);
		model.addAttribute("employee", employee);
		model.addAttribute("edit", true);
		return "update";
	}


	@RequestMapping(value = { "/edit-user-{empid}" }, method = RequestMethod.POST)
	public String UpdateUser(@Valid Employee emp, BindingResult result,
			ModelMap model, @PathVariable long empid) {

		System.out.println("For Updating");
		System.out.println("Employee : "+emp.toString());
		if (result.hasErrors()) {
			return "update";
		}
		System.out.println("Update");
		employeeService.UpdateEmployee(emp);
		model.addAttribute("success", "User " + emp.getName() + " updated successfully");

		return "registrationsuccess";
	}

	@RequestMapping(value = { "/delete-user-{empid}" }, method = RequestMethod.GET)
	public String DeleteUser(@PathVariable long empid,ModelMap model) {
		
		model.addAttribute("success", "User " + employeeService.deleteUserByID(empid) + " delete successfully");
		return "registrationsuccess";
	}

	@RequestMapping(value = { "/listtool" }, method = RequestMethod.GET)
	public ModelAndView ListTool() {
		ModelAndView mav=new ModelAndView("toolTip");
		List<Employee> list=employeeService.AllEmployee();
		LOGGER.info("List of Employees : "+list);
		mav.addObject("empObj",list);
		return mav;
	}
	
	@RequestMapping(value = { "/verification-{name}" }, method = RequestMethod.GET)
	public String CheckingVerification (@PathVariable String name) throws Exception {
	
		employeeService.SaveVerification(name);
		return "redirect:/loginpage";
	}
	
	@RequestMapping(value = { "/managepass-{name}" }, method = RequestMethod.GET)
	public String EmailPass (@PathVariable String name,Model model) throws Exception {
	
		if(!model.containsAttribute("employeeBean"))
			model.addAttribute("employeeBean",new Employee(SecurityUtility.Decrypt(name)));
		return "managepassword";
	}
	

	@RequestMapping(value = { "/passpage" }, method = RequestMethod.GET)
	public String PasswordPage (Model model) {
		System.out.println("In Forgot Password");
		if(!model.containsAttribute("employeeBean"))
			model.addAttribute("employeeBean",new Employee());

		return "forgotpassword";
	}
	
	
	@RequestMapping(value = { "/forgotpass" }, method = RequestMethod.POST)
	public ModelAndView ForgotPassword (@Valid @ModelAttribute("employeeBean") Employee employee,BindingResult bindingResult) throws Exception {
		EmailUtility.ForgotPassword(employee.getUsername());
		ModelAndView mav=new ModelAndView("forgotpassword");
		mav.addObject("mssg", "Password Change link is send to your Email-ID");
		return mav;

	}
	
	
	@RequestMapping(value = { "/savepass" }, method = RequestMethod.POST)
	public ModelAndView SavePassword (@Valid @ModelAttribute("employeeBean") Employee employee,BindingResult bindingResult) {
		employeeService.SavePassword(employee);
		ModelAndView mav=new ModelAndView("login");
		mav.addObject("message", "Your Password has been updated");
		return mav;
	}
	
	
	@RequestMapping(value = { "/message" }, method = RequestMethod.GET)
	public @ResponseBody String SendMessage (@RequestParam("id") String id) {
		System.out.println("Number : "+id);
		employeeService.SendMessage(id);
		System.out.println("Execution Complete..");
		return "true";
	}
	
	
	@RequestMapping(value = { "/validate" }, method = RequestMethod.GET)
	public @ResponseBody String VerfifyOTP (@RequestParam("id") String id) {
		System.out.println("OTP : "+id);
		if (UserContextFactory.getUserContext().getOtp()!=null) {

			if(UserContextFactory.getUserContext().getOtp().equals(id))
				return "true";
			else
				return "false";
		}
		else
		return "false";
	}

}