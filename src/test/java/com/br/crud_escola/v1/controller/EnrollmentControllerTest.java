package com.br.crud_escola.v1.controller;

import com.br.crud_escola.domain.dto.EnrollmentDTO;
import com.br.crud_escola.v1.service.EnrollmentService;
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
public class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnrollmentService enrollmentService;

    @Test
    public void givenValidEnrollmentDTO_whenCreateEnrollment_thenReturnCreatedEnrollment() throws Exception {
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO(1L, 1L);

        when(enrollmentService.createEnrollment(Mockito.anyLong(), Mockito.anyLong())).thenReturn(enrollmentDTO);

        mockMvc.perform(post("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentId\": 1, \"courseId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(1))
                .andExpect(jsonPath("$.courseId").value(1));
    }

    @Test
    public void givenValidStudentIdAndCourseId_whenRemoveEnrollment_thenReturnNoContent() throws Exception {
        Long studentId = 1L;
        Long courseId = 1L;

        Mockito.doNothing().when(enrollmentService).removeEnrollment(studentId, courseId);

        mockMvc.perform(delete("/api/enrollments/student/{studentId}/course/{courseId}", studentId, courseId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenGetAllEnrollments_thenReturnEnrollmentList() throws Exception {
        List<EnrollmentDTO> enrollments = Arrays.asList(
                new EnrollmentDTO(1L, 1L),
                new EnrollmentDTO(2L, 2L)
        );

        when(enrollmentService.getAllEnrollments()).thenReturn(enrollments);

        mockMvc.perform(get("/api/enrollments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].studentId").value(1))
                .andExpect(jsonPath("$[0].courseId").value(1))
                .andExpect(jsonPath("$[1].studentId").value(2))
                .andExpect(jsonPath("$[1].courseId").value(2));
    }
}

