package org.yunho.pharmacyrecommendation.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.yunho.pharmacyrecommendation.api.dto.DocumentDto;
import org.yunho.pharmacyrecommendation.api.dto.KakaoApiResponseDto;
import org.yunho.pharmacyrecommendation.api.dto.MetaDto;

import java.net.URI;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class KakaoAddressSearchServiceRetryTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;

    @MockBean
    private KakaoUriBuilderService kakaoUriBuilderService;

    private MockWebServer mockWebServer;

    private ObjectMapper mapper = new ObjectMapper();

    private String inputAddress = "서울 성북구 종암로 10길";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        System.out.println(mockWebServer.getPort());
        System.out.println(mockWebServer.url("/"));
    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void requestAddressSearchRetrySuccess() throws Exception {
        MetaDto metaDto = new MetaDto(1);
        DocumentDto documentDto = DocumentDto.builder()
                .addressName(inputAddress)
                .build();
        KakaoApiResponseDto expectedResponse = new KakaoApiResponseDto(metaDto, Arrays.asList(documentDto));
        URI uri = mockWebServer.url("/").uri();

        mockWebServer.enqueue(new MockResponse().setResponseCode(500));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(expectedResponse)));

        when(kakaoUriBuilderService.buildUriByAddressSearch(inputAddress)).thenReturn(uri);

        KakaoApiResponseDto kakaoApiResult = kakaoAddressSearchService.requestAddressSearch(inputAddress);

        verify(kakaoUriBuilderService, times(2)).buildUriByAddressSearch(inputAddress);
        assertEquals(kakaoApiResult.getDocumentList().size(), 1);
        assertEquals(kakaoApiResult.getMetaDto().getTotalCount(), 1);
        assertEquals(kakaoApiResult.getDocumentList().get(0).getAddressName(), inputAddress);
    }

    @Test
    void requestAddressSearchRetryFail() throws Exception {
        URI uri = mockWebServer.url("/").uri();

        mockWebServer.enqueue(new MockResponse().setResponseCode(500));
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        when(kakaoUriBuilderService.buildUriByAddressSearch(inputAddress)).thenReturn(uri);

        KakaoApiResponseDto result = kakaoAddressSearchService.requestAddressSearch(inputAddress);

        verify(kakaoUriBuilderService, times(2)).buildUriByAddressSearch(inputAddress);
        assertNull(result);
    }
}
