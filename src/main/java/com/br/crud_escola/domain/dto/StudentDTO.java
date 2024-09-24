package com.br.crud_escola.domain.dto;

import com.br.crud_escola.domain.enums.Gender;
import com.br.crud_escola.domain.model.Course;
import lombok.Builder;

import java.util.List;

@Builder
public record StudentDTO(String name,
                         Integer age,
                         String email) {}
