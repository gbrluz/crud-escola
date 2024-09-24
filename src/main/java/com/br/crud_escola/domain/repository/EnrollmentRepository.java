package com.br.crud_escola.domain.repository;

import com.br.crud_escola.domain.model.Course;
import com.br.crud_escola.domain.model.Enrollment;
import com.br.crud_escola.domain.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);

    boolean existsByStudentAndCourse(Student student, Course course);
}
