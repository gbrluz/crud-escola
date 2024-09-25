package com.br.crud_escola.v1.controller;

import com.br.crud_escola.domain.dto.EnrollmentDTO;
import com.br.crud_escola.v1.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Enroll a student in a course",
            description = "Enrolls a student in a specific course.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student enrolled successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EnrollmentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<EnrollmentDTO> createEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
        EnrollmentDTO createdEnrollment = enrollmentService.createEnrollment(enrollmentDTO.studentId(),enrollmentDTO.courseId());
        return ResponseEntity.ok(createdEnrollment);
    }

    @DeleteMapping("/student/{studentId}/course/{courseId}")
    @Operation(summary = "Remove a student's enrollment from a course",
            description = "Removes a student's enrollment from the specified course.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Enrollment removed successfully"),
            @ApiResponse(responseCode = "404", description = "Enrollment not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Void> removeEnrollment(@PathVariable Long studentId, @PathVariable Long courseId) {
        enrollmentService.removeEnrollment(studentId, courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get all enrollments",
            description = "Retrieves a list of all enrollments.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enrollments retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EnrollmentDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        List<EnrollmentDTO> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }
}
