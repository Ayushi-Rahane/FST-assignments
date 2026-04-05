package com.model;

import java.sql.*;

public class StudentDAO {
 public Student_data_container getStudent(int rollno) {
	 Student_data_container s = null;
	 try {
	       Class.forName("com.mysql.cj.jdbc.Driver");
	       Connection con = DriverManager.getConnection(
	    		    "jdbc:mysql://localhost:3306/studentdb",
	    		    "root",
	    		    "Root@123"
	    		);
	       PreparedStatement ps = con.prepareStatement("Select * from  student where rollno=?");
	       
	       ps.setInt(1,rollno);
	       
	       ResultSet rs = ps.executeQuery();
	       
	       if (rs.next()) {
	            s = new Student_data_container();
	            s.setrollno(rs.getInt("rollno"));
	            s.setAge(rs.getInt("Age"));
	            s.setStud_name(rs.getString("name")); // FIXED
	        }
	       con.close();
	 }
	 catch(Exception e) {
		 System.out.println(e);
	 }
	 return s;
 }
}
