package com.br.crud_escola.v1.service;

import com.br.crud_escola.domain.dto.EnrollmentDTO;
import com.br.crud_escola.domain.mappers.EnrollmentMapper;
import com.br.crud_escola.domain.model.Course;
import com.br.crud_escola.domain.model.Enrollment;
import com.br.crud_escola.domain.model.Student;
import com.br.crud_escola.domain.repository.CourseRepository;
import com.br.crud_escola.domain.repository.EnrollmentRepository;
import com.br.crud_escola.domain.repository.StudentRepository;
import com.br.crud_escola.v1.service.impl.EnrollmentServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrollmentMapper enrollmentMapper;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @Test
    void givenValidStudentAndCourseIds_whenCreateEnrollment_thenReturnEnrollmentDTO() {
        Long studentId = 1L;
        Long courseId = 1L;

        Student student = new Student();
        student.setId(studentId);

        Course course = new Course();
        course.setId(courseId);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        EnrollmentDTO enrollmentDTO = new EnrollmentDTO(studentId, courseId);

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        Mockito.when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(false);
        Mockito.when(enrollmentRepository.save(Mockito.any(Enrollment.class))).thenReturn(enrollment);
        Mockito.when(enrollmentMapper.toDTO(enrollment)).thenReturn(enrollmentDTO);

        EnrollmentDTO result = enrollmentService.createEnrollment(studentId, courseId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(enrollmentDTO, result);
        Mockito.verify(enrollmentRepository, Mockito.times(1)).save(Mockito.any(Enrollment.class));
    }

    @Test
    void givenExistingEnrollment_whenCreateEnrollment_thenThrowException() {
        Long studentId = 1L;
        Long courseId = 1L;

        Student student = new Student();
        student.setId(studentId);

        Course course = new Course();
        course.setId(courseId);

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        Mockito.when(enrollmentRepository.existsByStudentAndCourse(student, course)).thenReturn(true);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            enrollmentService.createEnrollment(studentId, courseId);
        });

        Assertions.assertEquals("Enrollment already exists for this student and course", exception.getMessage());
    }

    @Test
    void givenNonExistingStudent_whenCreateEnrollment_thenThrowEntityNotFoundException() {
        Long studentId = 1L;
        Long courseId = 1L;

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            enrollmentService.createEnrollment(studentId, courseId);
        });

        Assertions.assertEquals("Student not found with id " + studentId, exception.getMessage());
    }

    @Test
    void givenValidStudentAndCourseIds_whenRemoveEnrollment_thenEnrollmentIsDeleted() {
        Long studentId = 1L;
        Long courseId = 1L;

        Student student = new Student();
        student.setId(studentId);

        Course course = new Course();
        course.setId(courseId);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        Mockito.when(enrollmentRepository.findByStudentAndCourse(student, course)).thenReturn(Optional.of(enrollment));

        enrollmentService.removeEnrollment(studentId, courseId);

        Mockito.verify(enrollmentRepository, Mockito.times(1)).delete(enrollment);
    }

    @Test
    void givenNonExistingEnrollment_whenRemoveEnrollment_thenThrowEntityNotFoundException() {
        Long studentId = 1L;
        Long courseId = 1L;

        Student student = new Student();
        student.setId(studentId);

        Course course = new Course();
        course.setId(courseId);

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        Mockito.when(enrollmentRepository.findByStudentAndCourse(student, course)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            enrollmentService.removeEnrollment(studentId, courseId);
        });

        Assertions.assertEquals("Enrollment not found for student and course", exception.getMessage());
    }
}
