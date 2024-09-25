package com.br.crud_escola.v1.controller;

import com.br.crud_escola.domain.dto.TeacherDTO;
import com.br.crud_escola.v1.service.TeacherService;
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
@RequestMapping("/api/teachers")
@Tag(name = "Teachers", description = "Operations related to teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    @Operation(summary = "Add a new teacher",
            description = "Creates a new teacher with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TeacherDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        TeacherDTO createdTeacher = teacherService.createTeacher(teacherDTO);
        return ResponseEntity.ok(createdTeacher);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a teacher by ID",
            description = "Updates the details of an existing teacher by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TeacherDTO.class))),
            @ApiResponse(responseCode = "404", description = "Teacher not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Long id, @RequestBody TeacherDTO teacherDTO) {
        TeacherDTO updatedTeacher = teacherService.updateTeacher(id, teacherDTO);
        return ResponseEntity.ok(updatedTeacher);
    }

    @GetMapping
    @Operation(summary = "Get all teachers",
            description = "Retrieves a list of all teachers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teachers retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TeacherDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        List<TeacherDTO> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get teachers by course ID",
            description = "Retrieves teachers who are assigned to a specific course by course ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teachers found",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TeacherDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Course not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<List<TeacherDTO>> getTeachersByCourse(@PathVariable Long courseId) {
        List<TeacherDTO> teachers = teacherService.getTeachersByCourse(courseId);
        return ResponseEntity.ok(teachers);
    }
}
