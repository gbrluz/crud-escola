package com.br.crud_escola.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record TeacherDTO(String name,
                         Integer age) {}
