package com.br.crud_escola.domain.mappers;

import com.br.crud_escola.domain.dto.StudentDTO;
import com.br.crud_escola.domain.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDTO toDTO(Student student);

    Student toEntity(StudentDTO studentDTO);
}
