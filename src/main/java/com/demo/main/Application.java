package com.demo.main;

import java.util.List;

import com.demo.model.Employee;
import com.demo.service.EmployeeService;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EmployeeService service = new EmployeeService();
		
		Employee employee1 = new Employee("Tomas","Banks",40,7777);
		Employee updateEmployee = new Employee();
		updateEmployee.setId(99);
		updateEmployee.setSalary(12345.00);
		//parse employee objects
		//service.insert(employee1);
//		List<Employee> list = service.viewAll();
//		list.forEach(System.out::println);
		
		service.update(updateEmployee);
		
		List<Employee> list = service.viewAll();
		list.forEach(System.out::println);
	}

}
