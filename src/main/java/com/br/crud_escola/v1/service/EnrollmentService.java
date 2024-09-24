package com.br.crud_escola.v1.service;

import com.br.crud_escola.domain.dto.EnrollmentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO createEnrollment(Long studentId, Long courseId);
    void removeEnrollment(Long studentId, Long courseId);
    List<EnrollmentDTO> getAllEnrollments();
}
