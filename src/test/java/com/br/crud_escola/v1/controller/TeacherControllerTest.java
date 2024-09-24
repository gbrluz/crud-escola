package com.br.crud_escola.v1.controller;

import com.br.crud_escola.domain.dto.TeacherDTO;
import com.br.crud_escola.v1.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @Test
    public void givenValidTeacherDTO_whenCreateTeacher_thenReturnCreatedTeacher() throws Exception {
        TeacherDTO teacherDTO = new TeacherDTO("Gabriel", 45);

        when(teacherService.createTeacher(Mockito.any(TeacherDTO.class))).thenReturn(teacherDTO);

        mockMvc.perform(post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Gabriel\", \"age\": 45}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gabriel"))
                .andExpect(jsonPath("$.age").value(45));
    }

    @Test
    public void givenValidIdAndTeacherDTO_whenUpdateTeacher_thenReturnUpdatedTeacher() throws Exception {
        Long teacherId = 1L;
        TeacherDTO updatedTeacherDTO = new TeacherDTO("Gabriel", 50);

        when(teacherService.updateTeacher(Mockito.eq(teacherId), Mockito.any(TeacherDTO.class))).thenReturn(updatedTeacherDTO);

        mockMvc.perform(put("/api/teachers/{id}", teacherId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Gabriel\", \"age\": 50}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gabriel"))
                .andExpect(jsonPath("$.age").value(50));
    }

    @Test
    public void givenTeachers_whenGetAllTeachers_thenReturnTeacherList() throws Exception {
        List<TeacherDTO> teachers = Arrays.asList(
                new TeacherDTO("Gabriel", 45),
                new TeacherDTO("Marcelo", 40)
        );

        when(teacherService.getAllTeachers()).thenReturn(teachers);

        mockMvc.perform(get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Gabriel"))
                .andExpect(jsonPath("$[0].age").value(45))
                .andExpect(jsonPath("$[1].name").value("Marcelo"))
                .andExpect(jsonPath("$[1].age").value(40));
    }

    @Test
    public void givenValidCourseId_whenGetTeachersByCourse_thenReturnTeachersList() throws Exception {
        Long courseId = 1L;
        List<TeacherDTO> teachers = Arrays.asList(
                new TeacherDTO("Gabriel", 45),
                new TeacherDTO("Marcelo", 40)
        );

        when(teacherService.getTeachersByCourse(Mockito.eq(courseId))).thenReturn(teachers);

        mockMvc.perform(get("/api/teachers/course/{courseId}", courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Gabriel"))
                .andExpect(jsonPath("$[0].age").value(45))
                .andExpect(jsonPath("$[1].name").value("Marcelo"))
                .andExpect(jsonPath("$[1].age").value(40));
    }
}
