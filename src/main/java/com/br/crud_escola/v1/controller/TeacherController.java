package com.br.crud_escola.v1.controller;

import com.br.crud_escola.domain.dto.TeacherDTO;
import com.br.crud_escola.v1.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Add a new teacher")
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        TeacherDTO createdTeacher = teacherService.createTeacher(teacherDTO);
        return ResponseEntity.ok(createdTeacher);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a teacher by ID")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Long id, @RequestBody TeacherDTO teacherDTO) {
        TeacherDTO updatedTeacher = teacherService.updateTeacher(id, teacherDTO);
        return ResponseEntity.ok(updatedTeacher);
    }

    @GetMapping
    @Operation(summary = "Get all teachers")
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        List<TeacherDTO> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get teachers by course ID")
    public ResponseEntity<List<TeacherDTO>> getTeachersByCourse(@PathVariable Long courseId) {
        List<TeacherDTO> teachers = teacherService.getTeachersByCourse(courseId);
        return ResponseEntity.ok(teachers);
    }
}
