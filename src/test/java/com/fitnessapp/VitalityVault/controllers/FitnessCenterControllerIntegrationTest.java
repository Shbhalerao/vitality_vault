package com.fitnessapp.VitalityVault.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnessapp.VitalityVault.TestDataUtil;
import com.fitnessapp.VitalityVault.domain.dto.FitnessCenterDto;
import com.fitnessapp.VitalityVault.domain.entities.FitnessCenterEntity;
import com.fitnessapp.VitalityVault.services.FitnessCenterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class FitnessCenterControllerIntegrationTest {

    private FitnessCenterService fitnessCenterService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public FitnessCenterControllerIntegrationTest(MockMvc mockMvc
    ,FitnessCenterService fitnessCenterService, ObjectMapper objectMapper){
        this.fitnessCenterService = fitnessCenterService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testFitnessCenterCreationReturns201Created() throws Exception{
        FitnessCenterEntity fitnessCenterEntity = TestDataUtil
                .createFitnessCenterEntity_One();

        fitnessCenterEntity.setId(null);
        String json = objectMapper.writeValueAsString(fitnessCenterEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/fitness-centers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void testFitnessCenterCreationReturnsSavedCenter() throws Exception {
        FitnessCenterEntity testAuthorA = TestDataUtil.createFitnessCenterEntity_One();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.centerName").value("Golds Gym")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.locality").value("Teen hath naka")
        );
    }


}
