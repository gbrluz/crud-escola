package com.br.crud_escola.domain.dto;

import lombok.Builder;

@Builder
public record StudentUpdateDTO(String name,
                         Integer age) {}