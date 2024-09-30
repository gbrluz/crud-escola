package com.br.crud_escola.v1.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.br.crud_escola.domain.dto.CourseDTO;
import com.br.crud_escola.domain.dto.response.CourseResponseDTO;
import com.br.crud_escola.domain.model.Course;
import com.br.crud_escola.domain.mappers.CourseMapper;
import com.br.crud_escola.domain.repository.CourseRepository;
import com.br.crud_escola.v1.service.impl.CourseServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;
    private CourseDTO courseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        course = new Course();
        course.setName("Math");
        course.setDescription("Mathematics Course");
        course.setWorkload(40);

        courseDTO = new CourseDTO("Math", "Mathematics Course", 40);
    }

    @Test
    void testGetAllCourses() {
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        List<CourseDTO> courses = courseService.getAllCourses();

        assertEquals(1, courses.size());
        assertEquals("Math", courses.get(0).name());
    }

    @Test
    void testCreateCourse() {
        when(courseMapper.toEntity(courseDTO)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        CourseResponseDTO createdCourse = courseService.createCourse(courseDTO);

        assertNotNull(createdCourse);
        assertEquals("Math", createdCourse.name());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testUpdateCourse() {
        CourseDTO updatedCourseDTO = new CourseDTO("Math", "Updated Description", 50);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(courseMapper.toDTO(any(Course.class))).thenReturn(updatedCourseDTO);


        CourseResponseDTO updatedCourse = courseService.updateCourse(1L, updatedCourseDTO);

        assertEquals("Math", updatedCourse.name());
        assertEquals("Updated Description", updatedCourse.description());
        assertEquals(50, updatedCourse.workload());
    }

    @Test
    void testUpdateCourseThrowsExceptionWhenNameChanged() {
        CourseDTO updatedCourseDTO = new CourseDTO("Science", "Updated Description", 50);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.updateCourse(1L, updatedCourseDTO);
        });

        assertEquals("Course name cannot be changed", exception.getMessage());
    }

    @Test
    void testDeleteCourse() {
        doNothing().when(courseRepository).deleteById(1L);

        courseService.deleteCourse(1L);

        verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateCourseThrowsExceptionWhenCourseNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            courseService.updateCourse(1L, courseDTO);
        });

        assertEquals("Course not found", exception.getMessage());
    }
}
