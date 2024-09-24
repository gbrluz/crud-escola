package com.br.crud_escola.domain.dto;

import lombok.Builder;

@Builder
public record EnrollmentDTO(Long studentId,
                            Long courseId) {}
