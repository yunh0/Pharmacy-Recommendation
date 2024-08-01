package org.yunho.pharmacyrecommendation.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.yunho.pharmacyrecommendation.AbstractIntegrationContainerBaseTest;

import static org.junit.jupiter.api.Assertions.*;

public class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;

    @Test
    void givenNullAddress_whenRequestAddressSearch_thenReturnNull() {
        // Given
        String address = null;

        // When
        var result = kakaoAddressSearchService.requestAddressSearch(address);

        // Then
        assertNull(result);
    }

    @Test
    void givenValidAddress_whenRequestAddressSearch_thenReturnValidDocument() {
        // Given
        String address = "서울 성북구 종암로 10길";

        // When
        var result = kakaoAddressSearchService.requestAddressSearch(address);

        // Then
        assertTrue(result.getDocumentList().size() > 0);
        assertTrue(result.getMetaDto().getTotalCount() > 0);
        assertNotNull(result.getDocumentList().get(0).getAddressName());
    }
}
