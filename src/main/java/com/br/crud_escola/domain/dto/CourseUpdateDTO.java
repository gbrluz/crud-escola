package com.br.crud_escola.domain.dto;

import lombok.Builder;

@Builder
public record CourseUpdateDTO(String name,
                               Integer age) {}