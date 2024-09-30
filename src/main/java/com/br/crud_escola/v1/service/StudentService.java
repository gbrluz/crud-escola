package com.br.crud_escola.v1.service;

import com.br.crud_escola.domain.dto.StudentDTO;
import com.br.crud_escola.domain.dto.StudentUpdateDTO;
import com.br.crud_escola.domain.dto.response.StudentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StudentService {
    List<StudentDTO> getAllStudents();
    StudentResponseDTO createStudent(StudentDTO studentDTO);
    StudentResponseDTO updateStudent(Long id, StudentUpdateDTO studentDTO);
    void deleteStudent(Long id);
    List<StudentDTO> getStudentsByCourse(Long courseId);
}
