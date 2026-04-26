package com.example.studentapi.service;

import com.example.studentapi.model.Student;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * StudentService - Business logic layer.
 * Stores student records in an in-memory HashMap (rollNumber → Student).
 * Pre-loaded with 3 sample students for demo purposes.
 */
@Service
public class StudentService {

    // In-memory data store: key = rollNumber, value = Student object
    private final Map<Integer, Student> studentDatabase = new LinkedHashMap<>();

    // Constructor: pre-load sample data
    public StudentService() {
        studentDatabase.put(101, new Student(101, "Ayushi Rahane", 88.5));
        studentDatabase.put(102, new Student(102, "Rohit Sharma", 75.0));
        studentDatabase.put(103, new Student(103, "Priya Mehta", 92.3));
    }

    /**
     * GET all students
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentDatabase.values());
    }

    /**
     * GET student by roll number
     */
    public Student getStudentByRoll(int rollNumber) {
        return studentDatabase.get(rollNumber);
    }

    /**
     * POST - Add a new student
     * Returns false if roll number already exists.
     */
    public boolean addStudent(Student student) {
        if (studentDatabase.containsKey(student.getRollNumber())) {
            return false; // duplicate roll number
        }
        studentDatabase.put(student.getRollNumber(), student);
        return true;
    }

    /**
     * PUT - Update existing student details
     * Returns null if student not found.
     */
    public Student updateStudent(int rollNumber, Student updatedStudent) {
        if (!studentDatabase.containsKey(rollNumber)) {
            return null;
        }
        updatedStudent.setRollNumber(rollNumber); // keep roll number fixed
        studentDatabase.put(rollNumber, updatedStudent);
        return updatedStudent;
    }

    /**
     * DELETE - Remove student by roll number
     * Returns true if deleted, false if not found.
     */
    public boolean deleteStudent(int rollNumber) {
        if (!studentDatabase.containsKey(rollNumber)) {
            return false;
        }
        studentDatabase.remove(rollNumber);
        return true;
    }
}
