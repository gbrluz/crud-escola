package com.br.crud_escola.v1.service;

import com.br.crud_escola.domain.dto.TeacherDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TeacherService {
    List<TeacherDTO> getAllTeachers();
    TeacherDTO createTeacher(TeacherDTO teacherDTO);
    TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO);
    List<TeacherDTO> getTeachersByCourse(Long courseId);
}
