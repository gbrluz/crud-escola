package com.br.crud_escola.domain.dto.response;

import com.br.crud_escola.domain.model.Teacher;
import lombok.Builder;

import java.util.List;

@Builder
public record CourseResponseDTO (Long id,
                                 String name,
                                 String description,
                                 Integer workload,
                                 List<Teacher> teachers){}
