package org.yunho.pharmacyrecommendation.direction.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.yunho.pharmacyrecommendation.direction.controller.FormController;
import org.yunho.pharmacyrecommendation.direction.dto.OutputDto;
import org.yunho.pharmacyrecommendation.pharmacy.service.PharmacyRecommendationService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class FormControllerTest {

    @Autowired
    private FormController formController;

    @MockBean
    private PharmacyRecommendationService pharmacyRecommendationService;

    private MockMvc mockMvc;

    private List<OutputDto> outputDtoList;

    @BeforeEach
    void setUp() {
        outputDtoList = new ArrayList<>();
        outputDtoList.add(OutputDto.builder().pharmacyName("pharmacy1").build());
        outputDtoList.add(OutputDto.builder().pharmacyName("pharmacy2").build());

        mockMvc = MockMvcBuilders.standaloneSetup(formController)
                .build();
    }

    @Test
    void testGetMainPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(handler().handlerType(FormController.class))
                .andExpect(handler().methodName("main"))
                .andDo(print());
    }

    @Test
    void testPostSearch() throws Exception {
        String inputAddress = "서울 성북구 종암동";

        when(pharmacyRecommendationService.recommendPharmacyList(anyString())).thenReturn(outputDtoList);

        mockMvc.perform(post("/search")
                        .param("address", inputAddress))
                .andExpect(status().isOk())
                .andExpect(view().name("output"))
                .andExpect(model().attributeExists("outputFormList"))
                .andExpect(model().attribute("outputFormList", outputDtoList))
                .andDo(print());

        verify(pharmacyRecommendationService, times(1)).recommendPharmacyList(inputAddress);
    }
}

