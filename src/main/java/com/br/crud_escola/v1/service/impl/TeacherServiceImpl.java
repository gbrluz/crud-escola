package com.br.crud_escola.v1.service.impl;

import com.br.crud_escola.domain.dto.TeacherDTO;
import com.br.crud_escola.domain.mappers.TeacherMapper;
import com.br.crud_escola.domain.model.Teacher;
import com.br.crud_escola.domain.repository.TeacherRepository;
import com.br.crud_escola.v1.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }
    @Override
    public List<TeacherDTO> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream().map(teacherMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        teacherRepository.save(teacher);
        return teacherMapper.toDTO(teacher);
    }

    @Override
    public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) {
        Teacher existingTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        existingTeacher.setName(teacherDTO.name());
        existingTeacher.setAge(teacherDTO.age());
        teacherRepository.save(existingTeacher);
        return teacherMapper.toDTO(existingTeacher);
    }

    @Override
    public List<TeacherDTO> getTeachersByCourse(Long courseId) {
        List<Teacher> teachers = teacherRepository.findByCourses_Id(courseId);
        return teachers.stream().map(teacherMapper::toDTO).collect(Collectors.toList());
    }
}
