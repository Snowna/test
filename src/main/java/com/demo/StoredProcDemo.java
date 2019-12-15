package com.demo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

public class StoredProcDemo {
	Connection connection;

	public StoredProcDemo() {
		try {
			Class.forName("orcle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:orcle:thin:@172.17.29.96:1251:XE","system","oracle");
			if(connection != null) {
				System.out.println("Connected to DB!");
				CallableStatement cs = connection.prepareCall("{call sp3 (?,?,?)");
				cs.setInt(1, 125);
				cs.setInt(2, 50);
				cs.registerOutParameter(3, Types.INTEGER);
				cs.execute();
				int result = cs.getInt(3);
				System.out.println("Result: "+result);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new StoredProcDemo();
	}

}
