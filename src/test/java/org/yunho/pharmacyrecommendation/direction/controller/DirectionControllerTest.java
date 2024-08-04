package org.yunho.pharmacyrecommendation.direction.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.yunho.pharmacyrecommendation.direction.service.DirectionService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DirectionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DirectionService directionService;

    @InjectMocks
    private DirectionController directionController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(directionController)
                .build();
    }

    @Test
    void testGetDirectionRedirect() throws Exception {
        // Given
        String encodedId = "r";
        String redirectURL = "https://map.kakao.com/link/map/pharmacy,38.11,128.11";

        when(directionService.findDirectionUrlById(encodedId)).thenReturn(redirectURL);

        // When
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/dir/{encodedId}", encodedId));

        // Then
        result.andExpect(status().is3xxRedirection()) // 리다이렉트 발생 확인
                .andExpect(redirectedUrl(redirectURL))    // 리다이렉트 경로 검증
                .andDo(print());
    }
}
