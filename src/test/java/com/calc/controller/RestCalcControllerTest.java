package com.calc.controller;

import com.calc.controller.dto.TimeInterval;
import com.calc.entity.Calculation;
import com.calc.service.CalculationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestCalcController.class)
@AutoConfigureMockMvc(addFilters = false)
class RestCalcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculationService service;

    @Test
    void processExpressionMockMvcTest() throws Exception {
        String inputExpression = "2+2*2";
        Calculation result = new Calculation(5, inputExpression, new BigDecimal("6"), LocalDateTime.parse("2021-02-03T23:13:19.6136152"));
        String jsonString = "{\"id\":5,\"expression\":\"2+2*2\",\"result\":6,\"time\":\"2021-02-03T23:13:19.6136152\"}";

        when(service.process("2+2*2")).thenReturn(result);

        this.mockMvc.perform(post("/api/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputExpression))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(jsonString));
    }

    @Test
    void getAllinIntervalMockMvcTest() throws Exception {
        String inputJson = "{\"from\": \"2021-01-31T17:26:31.0879033\",\"to\": \"2021-01-31T17:16:34.2753216\"}";
        TimeInterval inputInterval = new TimeInterval(LocalDateTime.parse("2021-01-31T17:26:31.0879033"),
                                                        LocalDateTime.parse("2021-02-03T23:13:19.6136152"));
        List<Calculation> result = List.of(new Calculation(5, "2+2*2",
                                            new BigDecimal("6"), LocalDateTime.parse("2021-02-03T23:13:19.6136152")));

        when(service.findAllInTimeInterval(inputInterval)).thenReturn(result);

        this.mockMvc.perform(get("/api/timeInterval")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getListByOperationMockMvcTest() throws Exception {
        String operation = "*";
        List<Calculation> result = List.of(new Calculation(5, "2+2*2",
                                            new BigDecimal("6"), LocalDateTime.parse("2021-02-03T23:13:19.6136152")));
        String jsonString = "[{\"id\":5,\"expression\":\"2+2*2\",\"result\":6,\"time\":\"2021-02-03T23:13:19.6136152\"}]";

        when(service.findByOperation(operation)).thenReturn(result);

        this.mockMvc.perform(get("/api/operation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(operation))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonString))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}