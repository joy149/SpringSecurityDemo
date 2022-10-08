package com.example.demo.Student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private static final List<Student> studentList = Arrays.asList(
            new Student(1, "Joy Bhowmick"),
            new Student(2, "Shailly Bhowmick"),
            new Student(3, "Ishu Bagra"));

    @GetMapping("/student/{id}")
    public Student getStudentById (@PathVariable("id") int id) {
       return studentList.stream()
                         .filter(student -> id == student.getId())
                         .findFirst()
                         .orElseThrow(()-> new IllegalArgumentException("Student with Id: " +id+" does mot exist"));
    }
}
