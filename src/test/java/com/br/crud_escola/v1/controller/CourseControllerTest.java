package com.br.crud_escola.v1.controller;

import com.br.crud_escola.domain.dto.CourseDTO;
import com.br.crud_escola.v1.service.CourseService;
import org.junit.jupiter.api.Test;
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
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    public void givenValidCourseDTO_whenCreateCourse_thenReturnCreatedCourse() throws Exception {
        CourseDTO courseDTO = new CourseDTO("Mathematics", "Basic Math", 60);

        when(courseService.createCourse(courseDTO)).thenReturn(courseDTO);

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Mathematics\", \"description\": \"Basic Math\", \"workload\": 60}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mathematics"))
                .andExpect(jsonPath("$.description").value("Basic Math"))
                .andExpect(jsonPath("$.workload").value(60));
    }

    @Test
    public void givenValidIdAndCourseDTO_whenUpdateCourse_thenReturnUpdatedCourse() throws Exception {
        Long courseId = 1L;
        CourseDTO updatedCourseDTO = new CourseDTO("Mathematics", "Advanced Math", 120);

        when(courseService.updateCourse(courseId, updatedCourseDTO)).thenReturn(updatedCourseDTO);

        mockMvc.perform(put("/api/courses/{id}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Mathematics\", \"description\": \"Advanced Math\", \"workload\": 120}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mathematics"))
                .andExpect(jsonPath("$.description").value("Advanced Math"))
                .andExpect(jsonPath("$.workload").value(120));
    }

    @Test
    public void givenValidId_whenDeleteCourse_thenReturnNoContent() throws Exception {
        Long courseId = 1L;

        mockMvc.perform(delete("/api/courses/{id}", courseId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenCourses_whenGetAllCourses_thenReturnCourseList() throws Exception {
        List<CourseDTO> courses = Arrays.asList(
                new CourseDTO("Mathematics", "Basic Math", 60),
                new CourseDTO("Physics", "Basic Physics", 45)
        );

        when(courseService.getAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Mathematics"))
                .andExpect(jsonPath("$[0].description").value("Basic Math"))
                .andExpect(jsonPath("$[0].workload").value(60))
                .andExpect(jsonPath("$[1].name").value("Physics"))
                .andExpect(jsonPath("$[1].description").value("Basic Physics"))
                .andExpect(jsonPath("$[1].workload").value(45));
    }
}
