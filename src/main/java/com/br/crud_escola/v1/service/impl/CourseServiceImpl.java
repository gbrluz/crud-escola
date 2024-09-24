package com.br.crud_escola.v1.service.impl;

import com.br.crud_escola.domain.dto.CourseDTO;
import com.br.crud_escola.domain.mappers.CourseMapper;
import com.br.crud_escola.domain.model.Course;
import com.br.crud_escola.domain.repository.CourseRepository;
import com.br.crud_escola.v1.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }
    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(courseMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = courseMapper.toEntity(courseDTO);
        courseRepository.save(course);
        return courseMapper.toDTO(course);
    }

    @Override
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        if (!existingCourse.getName().equals(courseDTO.name())) {
            throw new IllegalArgumentException("Course name cannot be changed");
        }

        existingCourse.setDescription(courseDTO.description());
        existingCourse.setWorkload(courseDTO.workload());
        courseRepository.save(existingCourse);
        return courseMapper.toDTO(existingCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
