package com.br.crud_escola.domain.dto.response;

import com.br.crud_escola.domain.enums.Gender;
import lombok.Builder;

@Builder
public record StudentResponseDTO(Long id,
                                 String name,
                                 Integer age,
                                 String email,
                                 Gender gender) {}
