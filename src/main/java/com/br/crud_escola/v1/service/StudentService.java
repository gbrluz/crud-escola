package com.br.crud_escola.v1.service;

import com.br.crud_escola.domain.dto.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StudentService {
    List<StudentDTO> getAllStudents();
    StudentDTO createStudent(StudentDTO studentDTO);
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);
    void deleteStudent(Long id);
    List<StudentDTO> getStudentsByCourse(Long courseId);
}
