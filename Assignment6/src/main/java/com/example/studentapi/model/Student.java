package com.example.studentapi.model;

/**
 * Student Model - Represents a student record.
 * Fields: rollNumber (unique ID), name, marks
 */
public class Student {

    private int rollNumber;
    private String name;
    private double marks;

    // Default constructor (required for JSON deserialization)
    public Student() {}

    // Parameterized constructor
    public Student(int rollNumber, String name, double marks) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.marks = marks;
    }

    // --- Getters and Setters ---

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Student{rollNumber=" + rollNumber + ", name='" + name + "', marks=" + marks + "}";
    }
}
