package com.br.crud_escola.v1.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.br.crud_escola.domain.dto.TeacherDTO;
import com.br.crud_escola.domain.model.Teacher;
import com.br.crud_escola.domain.mappers.TeacherMapper;
import com.br.crud_escola.domain.repository.TeacherRepository;
import com.br.crud_escola.v1.service.impl.TeacherServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    private Teacher teacher;
    private TeacherDTO teacherDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        teacher = new Teacher();
        teacher.setName("Gabriel Luz");
        teacher.setAge(45);

        teacherDTO = new TeacherDTO("Gabriel Luz", 45);
    }

    @Test
    void testGetAllTeachers() {
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher));
        when(teacherMapper.toDTO(teacher)).thenReturn(teacherDTO);

        List<TeacherDTO> teachers = teacherService.getAllTeachers();

        assertEquals(1, teachers.size());
        assertEquals("Gabriel Luz", teachers.get(0).name());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    void testCreateTeacher() {
        when(teacherMapper.toEntity(teacherDTO)).thenReturn(teacher);
        when(teacherRepository.save(teacher)).thenReturn(teacher);
        when(teacherMapper.toDTO(teacher)).thenReturn(teacherDTO);

        TeacherDTO createdTeacher = teacherService.createTeacher(teacherDTO);

        assertNotNull(createdTeacher);
        assertEquals("Gabriel Luz", createdTeacher.name());
        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    void testUpdateTeacher() {

        TeacherDTO updatedTeacherDTO = new TeacherDTO("Gabriel Luz", 50);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
        when(teacherMapper.toDTO(teacher)).thenReturn(updatedTeacherDTO);


        TeacherDTO updatedTeacher = teacherService.updateTeacher(1L, updatedTeacherDTO);

        assertEquals("Gabriel Luz", updatedTeacher.name());
        assertEquals(50, updatedTeacher.age());
        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    void testUpdateTeacherThrowsExceptionWhenTeacherNotFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            teacherService.updateTeacher(1L, teacherDTO);
        });

        assertEquals("Teacher not found", exception.getMessage());
    }

    @Test
    void testGetTeachersByCourse() {
        when(teacherRepository.findByCourses_Id(1L)).thenReturn(Arrays.asList(teacher));
        when(teacherMapper.toDTO(teacher)).thenReturn(teacherDTO);

        List<TeacherDTO> teachers = teacherService.getTeachersByCourse(1L);

        assertEquals(1, teachers.size());
        assertEquals("Gabriel Luz", teachers.get(0).name());
        verify(teacherRepository, times(1)).findByCourses_Id(1L);
    }
}
