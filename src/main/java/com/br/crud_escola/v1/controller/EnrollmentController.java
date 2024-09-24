package com.br.crud_escola.v1.controller;

import com.br.crud_escola.domain.dto.EnrollmentDTO;
import com.br.crud_escola.v1.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@Tag(name = "Enrollments", description = "Operations related to enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    @Operation(summary = "Enroll a student in a course")
    public ResponseEntity<EnrollmentDTO> createEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
        EnrollmentDTO createdEnrollment = enrollmentService.createEnrollment(enrollmentDTO.studentId(),enrollmentDTO.studentId());
        return ResponseEntity.ok(createdEnrollment);
    }

    @DeleteMapping("/student/{studentId}/course/{courseId}")
    @Operation(summary = "Remove a student's enrollment from a course")
    public ResponseEntity<Void> removeEnrollment(@PathVariable Long studentId, @PathVariable Long courseId) {
        enrollmentService.removeEnrollment(studentId, courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get all enrollments")
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        List<EnrollmentDTO> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }
}
