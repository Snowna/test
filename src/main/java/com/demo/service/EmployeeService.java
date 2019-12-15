package com.demo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.demo.model.Employee;

public class EmployeeService {
	
	Connection connection;
	Statement statement;
	
	public EmployeeService() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nov2019","root","1020zxnH");
			if(connection != null) {
				System.out.println("Connected to DB!");
				statement = connection.createStatement();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void insert(Employee employee) {
		try {
//			int num = statement.executeUpdate("insert into employees(firstName,lastName,age,salary) values('"+employee.getFirstName()+"', '"+employee.getLastName()+"', '"+
//					employee.getAge()+"', '"+employee.getSalary()+"')");
			// too ugly to write like above 
			PreparedStatement ps = connection.prepareStatement("insert into employees(firstName,lastName,age,salary) values(?,?,?,?)");
			ps.setString(1, employee.getFirstName());
			ps.setString(2, employee.getLastName());
			ps.setInt(3, employee.getAge());
			ps.setDouble(4, employee.getSalary());
			int num = ps.executeUpdate();
			if(num>0) {
				System.out.println("Inserted!");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//need primary key
	
	public void update(Employee employee) {
		try {
			PreparedStatement ps = connection.prepareStatement("update employees set salary=? where id=?");
			ps.setDouble(1, employee.getSalary());
			ps.setInt(2, employee.getId());
			ps.executeUpdate();
			System.out.println("Row Updated!");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Employee> viewAll(){
		List<Employee> list = new ArrayList<Employee>();
		try {
			PreparedStatement ps = connection.prepareStatement("select * from employees");
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next()) {
				Employee emp = new Employee();

				for(int i = 1;i<=columnCount;i++) {
					int columnType = rsmd.getColumnType(i);
					switch(columnType) {
					case Types.INTEGER:{
						if(i==1) {
							emp.setId(rs.getInt(i));
						}else if(i==4) {
							emp.setAge(rs.getInt(i));
						}
						break;
					}
					case Types.VARCHAR:{
						//System.out.println("123");
						if(i==2) {
							emp.setFirstName(rs.getString(i));
						}else  {
							emp.setLastName(rs.getString(i));
						}
						break;
					}
					case Types.DOUBLE:{
						emp.setSalary(rs.getDouble(i));
						break;
					}
					}
				}
				
				//Employee emp = new Employee(rs.getString(2),rs.getString(3),rs.getInt(4),rs.getDouble(5));
				//emp.setId(rs.getInt(1));
				list.add(emp);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
