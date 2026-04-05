package com.model;

public class Student_data_container {
private int rollno, age, marks;
private String stud_name, course;

//Getter: provides data
public int getrollno() {
	return rollno;
}

public int getAge() {
	return age;
}

public int getMarks() {
	return marks;
}

public String getStud_name() {
	return stud_name;
}

public String getCourse() {
	return course;
}
//Setter: sets the data

public void setrollno(int rollno) {
	this.rollno = rollno;
}

public void setAge(int age) {
	this.age = age;
}

public void setMarks(int mark) {
	this.marks = mark;
}

public void setStud_name(String studname) {
	this.stud_name = studname;
}

public void setCourse(String course) {
	this.course = course;
}
}
