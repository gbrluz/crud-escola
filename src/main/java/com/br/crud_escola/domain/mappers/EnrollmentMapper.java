package com.br.crud_escola.domain.mappers;

import com.br.crud_escola.domain.dto.EnrollmentDTO;
import com.br.crud_escola.domain.model.Enrollment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    EnrollmentDTO toDTO(Enrollment enrollment);

    Enrollment toEntity(EnrollmentDTO enrollmentDTO);
}
