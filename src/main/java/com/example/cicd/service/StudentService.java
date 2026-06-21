package com.example.cicd.service;

import com.example.cicd.entity.Student;
import com.example.cicd.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return repository.findById(id);
    }

    public Student createStudent(Student student) {
        // Business logic — check duplicate email
        boolean emailExists = repository.findAll()
                .stream()
                .anyMatch(s -> s.getEmail().equalsIgnoreCase(student.getEmail()));

        if (emailExists) {
            throw new RuntimeException("Email already exists: " + student.getEmail());
        }

        return repository.save(student);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));

        existing.setName(updatedStudent.getName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setCourse(updatedStudent.getCourse());

        return repository.save(existing);
    }

    public void deleteStudent(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Student not found: " + id);
        }
        repository.deleteById(id);
    }

    public long getTotalStudents() {
        return repository.count();
    }
}
