package org.yunho.pharmacyrecommendation.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class KakaoUriBuilderServiceTest {

    private KakaoUriBuilderService kakaoUriBuilderService;

    @BeforeEach
    void setup() {
        kakaoUriBuilderService = new KakaoUriBuilderService();
    }

    @Test
    void buildUriByAddressSearch_HangulParameterIsProperlyEncoded() throws Exception {
        // Given
        String address = "서울 성북구";
        Charset charset = StandardCharsets.UTF_8;

        // When
        URI uri = kakaoUriBuilderService.buildUriByAddressSearch(address);
        String decodedResult = URLDecoder.decode(uri.toString(), charset);

        // Then
        assertEquals("https://dapi.kakao.com/v2/local/search/address.json?query=서울 성북구", decodedResult);
    }
}