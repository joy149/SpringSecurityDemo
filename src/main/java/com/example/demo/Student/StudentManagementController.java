package com.example.demo.Student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api")
public class StudentManagementController {

    private static List<Student> studentList = new ArrayList<>(Arrays.asList(
            new Student(1, "Joy Bhowmick"),
            new Student(2, "Shailly Bhowmick"),
            new Student(3, "Ishu Bagra")));

    @GetMapping("/student")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents() {
        return studentList;
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('student:write')")
    public Student createStudent(@RequestBody Student student) {
         studentList.add(student);
        return student;
    }

}
