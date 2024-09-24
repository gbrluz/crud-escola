package com.br.crud_escola.domain.dto;

import lombok.Builder;

@Builder
public record CourseDTO (String name,
                         String description,
                         Integer workload){}
