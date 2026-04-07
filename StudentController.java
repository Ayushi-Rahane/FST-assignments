package com.example.studentapi;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    List<Student_data_container> students = new ArrayList<>();

    // GET → View all students
    @GetMapping
    public List<Student_data_container> getAllStudents() {
        return students;
    }

    // POST → Add student
    @PostMapping
    public String addStudent(@RequestBody Student_data_container s) {
        students.add(s);
        return "Student added";
    }

    // PUT → Update student
    @PutMapping("/{rollNo}")
    public String updateStudent(@PathVariable int rollNo, @RequestBody Student_data_container updated) {
        for (Student_data_container s : students) {
            if (s.getRollno() == rollNo) {
                s.setName(updated.getName());
                s.setMarks(updated.getMarks());
                return "Updated";
            }
        }
        return "Not found";
    }

    // DELETE → Remove student
    @DeleteMapping("/{rollNo}")
    public String deleteStudent(@PathVariable int rollNo) {
        students.removeIf(s -> s.getRollno() == rollNo);
        return "Deleted";
    }
}