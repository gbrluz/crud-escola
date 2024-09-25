package com.br.crud_escola.v1.service;

import com.br.crud_escola.domain.dto.StudentDTO;
import com.br.crud_escola.domain.mappers.StudentMapper;
import com.br.crud_escola.domain.model.Student;
import com.br.crud_escola.domain.repository.StudentRepository;
import com.br.crud_escola.v1.service.impl.StudentServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private StudentDTO studentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student(); // Supondo que você tenha um construtor padrão
        student.setEmail("email@teste.com");
        student.setName("Gabriel");
        student.setAge(20);

        studentDTO = new StudentDTO("Gabriel", 20, "email@teste.com");
    }

    @Test
    void testGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));
        when(studentMapper.toDTO(student)).thenReturn(studentDTO);

        List<StudentDTO> students = studentService.getAllStudents();

        assertEquals(1, students.size());
        assertEquals("Gabriel", students.get(0).name());
    }

    @Test
    void testCreateStudent() {
        when(studentRepository.findByEmail(studentDTO.email())).thenReturn(Optional.empty());
        when(studentMapper.toEntity(studentDTO)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.toDTO(student)).thenReturn(studentDTO);

        StudentDTO createdStudent = studentService.createStudent(studentDTO);

        assertNotNull(createdStudent);
        assertEquals("Gabriel", createdStudent.name());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testCreateStudentThrowsExceptionWhenEmailExists() {
        when(studentRepository.findByEmail(studentDTO.email())).thenReturn(Optional.of(student));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            studentService.createStudent(studentDTO);
        });

        assertEquals("Email already exists!", exception.getMessage());
    }

    @Test
    void testUpdateStudent() {

        StudentDTO updatedStudentDTO = new StudentDTO("Gabriel", 21, "email@teste.com");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentMapper.toDTO(student)).thenReturn(updatedStudentDTO);


        StudentDTO updatedStudent = studentService.updateStudent(1L, updatedStudentDTO);

        assertEquals("Gabriel", updatedStudent.name());
        assertEquals(21, updatedStudent.age());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testUpdateStudentThrowsExceptionWhenEmailChanged() {
        StudentDTO updatedStudentDTO = new StudentDTO("Gabriel", 21, "newemail@teste.com");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudent(1L, updatedStudentDTO);
        });

        assertEquals("Email cannot be changed", exception.getMessage());
    }

    @Test
    void testUpdateStudentThrowsExceptionWhenStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            studentService.updateStudent(1L, studentDTO);
        });

        assertEquals("Student not found", exception.getMessage());
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetStudentsByCourse() {
        when(studentRepository.findByCourses_Id(1L)).thenReturn(Arrays.asList(student));
        when(studentMapper.toDTO(student)).thenReturn(studentDTO);

        List<StudentDTO> students = studentService.getStudentsByCourse(1L);

        assertEquals(1, students.size());
        assertEquals("Gabriel", students.get(0).name());
    }
}