package com.example.studentapi;

public class Student_data_container {
    private int rollno, marks;
    private String name;
    
   public Student_data_container() {} //Spring converts JSON into object by first creating empty object using default constructor and then setting values.
   	
   public Student_data_container(int rollno, int marks, String name) {
	   this.rollno = rollno;
	   this.marks = marks;
	   this.name = name;
   }
   
   public int getRollno() {
	    return rollno;
	}

	public void setRollno(int rollno) {
	    this.rollno = rollno;
	}

	public int getMarks() {
	    return marks;
	}

	public void setMarks(int marks) {
	    this.marks = marks;
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}
}
