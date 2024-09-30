package com.br.crud_escola.domain.mappers;

import com.br.crud_escola.domain.dto.StudentDTO;
import com.br.crud_escola.domain.dto.StudentUpdateDTO;
import com.br.crud_escola.domain.dto.response.StudentResponseDTO;
import com.br.crud_escola.domain.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDTO toDTO(Student student);

    StudentUpdateDTO updateEntityToDTO(Student student);

    StudentResponseDTO EntityToResponseDTO (Student student);

    Student updateDTOToEntity(StudentUpdateDTO studentUpdateDTO);

    Student toEntity(StudentDTO studentDTO);
}
