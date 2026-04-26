package com.example.studentapi.controller;

import com.example.studentapi.model.Student;
import com.example.studentapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StudentController - REST Controller for Student Management API
 *
 * Base URL: /api/students
 *
 * Endpoints:
 *   GET    /api/students          → View all students
 *   GET    /api/students/{roll}   → View student by roll number
 *   POST   /api/students          → Add a new student
 *   PUT    /api/students/{roll}   → Update student details
 *   DELETE /api/students/{roll}   → Delete a student
 *
 * @RestController = @Controller + @ResponseBody (auto-converts to JSON)
 * @CrossOrigin allows the HTML frontend to call this API from the browser
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // ─────────────────────────────────────────────
    // GET /api/students → Retrieve ALL students
    // ─────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students); // 200 OK
    }

    // ─────────────────────────────────────────────
    // GET /api/students/{rollNumber} → Retrieve ONE student
    // ─────────────────────────────────────────────
    @GetMapping("/{rollNumber}")
    public ResponseEntity<?> getStudentByRoll(@PathVariable int rollNumber) {
        Student student = studentService.getStudentByRoll(rollNumber);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorResponse("Student with Roll No " + rollNumber + " not found."));
        }
        return ResponseEntity.ok(student); // 200 OK
    }

    // ─────────────────────────────────────────────
    // POST /api/students → Add a NEW student
    // ─────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        boolean added = studentService.addStudent(student);
        if (!added) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(errorResponse("Student with Roll No " + student.getRollNumber() + " already exists."));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(student); // 201 Created
    }

    // ─────────────────────────────────────────────
    // PUT /api/students/{rollNumber} → UPDATE existing student
    // ─────────────────────────────────────────────
    @PutMapping("/{rollNumber}")
    public ResponseEntity<?> updateStudent(@PathVariable int rollNumber, @RequestBody Student student) {
        Student updated = studentService.updateStudent(rollNumber, student);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorResponse("Student with Roll No " + rollNumber + " not found."));
        }
        return ResponseEntity.ok(updated); // 200 OK
    }

    // ─────────────────────────────────────────────
    // DELETE /api/students/{rollNumber} → REMOVE student
    // ─────────────────────────────────────────────
    @DeleteMapping("/{rollNumber}")
    public ResponseEntity<?> deleteStudent(@PathVariable int rollNumber) {
        boolean deleted = studentService.deleteStudent(rollNumber);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorResponse("Student with Roll No " + rollNumber + " not found."));
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student with Roll No " + rollNumber + " deleted successfully.");
        return ResponseEntity.ok(response); // 200 OK
    }

    // ─────────────────────────────────────────────
    // Helper: build error response map
    // ─────────────────────────────────────────────
    private Map<String, String> errorResponse(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return error;
    }
}
