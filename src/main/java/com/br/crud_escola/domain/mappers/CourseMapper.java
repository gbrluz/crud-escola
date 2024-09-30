package com.br.crud_escola.domain.mappers;

import com.br.crud_escola.domain.dto.CourseDTO;
import com.br.crud_escola.domain.dto.response.CourseResponseDTO;
import com.br.crud_escola.domain.dto.response.StudentResponseDTO;
import com.br.crud_escola.domain.model.Course;
import com.br.crud_escola.domain.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO toDTO(Course course);

    CourseResponseDTO entityToResponseDTO (Course course);

    Course toEntity(CourseDTO courseDTO);
}

