package com.br.crud_escola.domain.mappers;

import com.br.crud_escola.domain.dto.TeacherDTO;
import com.br.crud_escola.domain.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherDTO toDTO(Teacher teacher);

    Teacher toEntity(TeacherDTO teacherDTO);
}

