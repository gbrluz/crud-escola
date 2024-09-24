package com.br.crud_escola.v1.controller;

import com.br.crud_escola.domain.dto.StudentDTO;
import com.br.crud_escola.v1.service.StudentService;
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
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void givenValidStudentDTO_whenCreateStudent_thenReturnCreatedStudent() throws Exception {
        StudentDTO studentDTO = new StudentDTO("Gabriel", 20, "email@teste.com");

        when(studentService.createStudent(Mockito.any(StudentDTO.class))).thenReturn(studentDTO);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Gabriel\", \"age\": 20}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gabriel"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    public void givenValidIdAndStudentDTO_whenUpdateStudent_thenReturnUpdatedStudent() throws Exception {
        Long studentId = 1L;
        StudentDTO updatedStudentDTO = new StudentDTO("Gabriel", 22, "email@teste.com");

        when(studentService.updateStudent(Mockito.eq(studentId), Mockito.any(StudentDTO.class))).thenReturn(updatedStudentDTO);

        mockMvc.perform(put("/api/students/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Gabriel\", \"age\": 22}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gabriel"))
                .andExpect(jsonPath("$.age").value(22));
    }

    @Test
    public void givenStudents_whenGetAllStudents_thenReturnStudentList() throws Exception {
        List<StudentDTO> students = Arrays.asList(
                new StudentDTO("Gabriel", 20,"email@teste.com"),
                new StudentDTO("Marcelo", 21, "email2@teste.com")
        );

        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Gabriel"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[1].name").value("Marcelo"))
                .andExpect(jsonPath("$[1].age").value(21));
    }

    @Test
    public void givenValidCourseId_whenGetStudentsByCourse_thenReturnStudentList() throws Exception {
        Long courseId = 1L;
        List<StudentDTO> students = Arrays.asList(
                new StudentDTO("Gabriel", 20, "email@teste.com"),
                new StudentDTO("Marcelo", 21, "email2@teste.com")
        );

        when(studentService.getStudentsByCourse(Mockito.eq(courseId))).thenReturn(students);

        mockMvc.perform(get("/api/students/course/{courseId}", courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Gabriel"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[1].name").value("Marcelo"))
                .andExpect(jsonPath("$[1].age").value(21));
    }

    @Test
    public void givenValidId_whenDeleteStudent_thenReturnNoContent() throws Exception {
        Long studentId = 1L;

        Mockito.doNothing().when(studentService).deleteStudent(studentId);

        mockMvc.perform(delete("/api/students/{id}", studentId))
                .andExpect(status().isNoContent());
    }
}

