package com.br.crud_escola.v1.service.impl;

import com.br.crud_escola.domain.dto.StudentDTO;
import com.br.crud_escola.domain.dto.StudentUpdateDTO;
import com.br.crud_escola.domain.dto.response.StudentResponseDTO;
import com.br.crud_escola.domain.mappers.StudentMapper;
import com.br.crud_escola.domain.model.Student;
import com.br.crud_escola.domain.repository.StudentRepository;
import com.br.crud_escola.v1.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }
    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(studentMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public StudentResponseDTO createStudent(StudentDTO studentDTO) {
        if (studentRepository.findByEmail(studentDTO.email()).isPresent()) {
            throw new IllegalArgumentException("Email already exists!");
        }
        Student student = studentMapper.toEntity(studentDTO);
        studentRepository.save(student);
        return studentMapper.EntityToResponseDTO(student);
    }

    @Override
    public StudentResponseDTO updateStudent(Long id, StudentUpdateDTO studentDTO) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        studentRepository.save(studentMapper.updateDTOToEntity(studentDTO));
        return studentMapper.EntityToResponseDTO(existingStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        studentRepository.deleteById(id);
    }

    @Override
    public List<StudentDTO> getStudentsByCourse(Long courseId) {
        List<Student> students = studentRepository.findByCourses_Id(courseId);
        return students.stream().map(studentMapper::toDTO).collect(Collectors.toList());
    }
}
