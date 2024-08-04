package org.yunho.pharmacyrecommendation.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @ParameterizedTest
    @CsvSource({
            "서울 특별시 성북구 종암동, true",
            "서울 성북구 종암동 91, true",
            "서울 대학로, true",
            "서울 성북구 종암동 잘못된 주소, false",
            "광진구 구의동 251-45, true",
            "광진구 구의동 251-455555, false",
            "'', false"
    })
    void testAddressSearch(String inputAddress, boolean expectedResult) {
        // when
        var searchResult = kakaoAddressSearchService.requestAddressSearch(inputAddress);

        // then
        if (searchResult == null) {
            assertFalse(expectedResult);
        } else {
            assertNotNull(searchResult.getDocumentList());
            assertTrue(searchResult.getDocumentList().size() > 0 == expectedResult);
        }
    }
}
