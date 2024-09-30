package com.br.crud_escola.v1.service;

import com.br.crud_escola.domain.dto.CourseDTO;
import com.br.crud_escola.domain.dto.response.CourseResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAllCourses();
    CourseResponseDTO createCourse(CourseDTO courseDTO);
    CourseResponseDTO updateCourse(Long id, CourseDTO courseDTO);
    void deleteCourse(Long id);
}
