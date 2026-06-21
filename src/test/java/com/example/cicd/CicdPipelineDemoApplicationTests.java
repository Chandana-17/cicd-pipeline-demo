package com.example.cicd;

import com.example.cicd.entity.Student;
import com.example.cicd.repository.StudentRepository;
import com.example.cicd.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CicdPipelineDemoApplicationTests {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private StudentService studentService;

    @Test
    void contextLoads() {
        assertNotNull(repository);
        assertNotNull(studentService);
    }

    @Test
    void studentModelTest() {
        Student student = new Student(null, "Alice", "alice@test.com", "CS");
        assertEquals("Alice", student.getName());
        assertEquals("alice@test.com", student.getEmail());
        assertEquals("CS", student.getCourse());
    }

    @Test
    void createStudentTest() {
        Student student = new Student(null, "Test User", "testunique@test.com", "DevOps");
        Student saved = studentService.createStudent(student);
        assertNotNull(saved.getId());
        assertEquals("Test User", saved.getName());
    }

    @Test
    void getAllStudentsTest() {
        var students = studentService.getAllStudents();
        assertNotNull(students);
        assertTrue(students.size() >= 0);
    }

    @Test
    void duplicateEmailTest() {
        Student s1 = new Student(null, "User One", "duplicate@test.com", "CS");
        studentService.createStudent(s1);

        Student s2 = new Student(null, "User Two", "duplicate@test.com", "IT");
        assertThrows(RuntimeException.class, () -> studentService.createStudent(s2));
    }

}
