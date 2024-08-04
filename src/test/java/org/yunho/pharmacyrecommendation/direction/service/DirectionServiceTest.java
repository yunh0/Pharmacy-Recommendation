package org.yunho.pharmacyrecommendation.direction.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yunho.pharmacyrecommendation.api.dto.DocumentDto;
import org.yunho.pharmacyrecommendation.api.service.KakaoCategorySearchService;
import org.yunho.pharmacyrecommendation.direction.entity.Direction;
import org.yunho.pharmacyrecommendation.direction.repository.DirectionRepository;
import org.yunho.pharmacyrecommendation.pharmacy.dto.PharmacyDto;
import org.yunho.pharmacyrecommendation.pharmacy.service.PharmacySearchService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DirectionServiceTest {

    @Mock
    private PharmacySearchService pharmacySearchService;

    @Mock
    private DirectionRepository directionRepository;

    @Mock
    private KakaoCategorySearchService kakaoCategorySearchService;

    @Mock
    private Base62Service base62Service;

    @InjectMocks
    private DirectionService directionService;

    private List<PharmacyDto> pharmacyList;

    @BeforeEach
    void setUp() {
        pharmacyList = new ArrayList<>();
        pharmacyList.add(PharmacyDto.builder()
                .id(1L)
                .pharmacyName("돌곶이온누리약국")
                .pharmacyAddress("주소1")
                .latitude(37.61040424)
                .longitude(127.0569046)
                .build());
        pharmacyList.add(PharmacyDto.builder()
                .id(2L)
                .pharmacyName("호수온누리약국")
                .pharmacyAddress("주소2")
                .latitude(37.60894036)
                .longitude(127.029052)
                .build());
    }

    @Test
    void buildDirectionList_shouldBeSortedByDistance() {
        // Given
        String addressName = "서울 성북구 종암로10길";
        double inputLatitude = 37.5960650456809;
        double inputLongitude = 127.037033003036;

        DocumentDto documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build();

        when(pharmacySearchService.searchPharmacyDtoList()).thenReturn(pharmacyList);

        // When
        List<Direction> results = directionService.buildDirectionList(documentDto);

        // Then
        assertEquals(2, results.size());
        assertEquals("호수온누리약국", results.get(0).getTargetPharmacyName());
        assertEquals("돌곶이온누리약국", results.get(1).getTargetPharmacyName());
    }

    @Test
    void buildDirectionList_shouldSearchWithinRadius() {
        // Given
        pharmacyList.add(PharmacyDto.builder()
                .id(3L)
                .pharmacyName("경기약국")
                .pharmacyAddress("주소3")
                .latitude(37.3825107393401)
                .longitude(127.236707811313)
                .build());

        String addressName = "서울 성북구 종암로10길";
        double inputLatitude = 37.5960650456809;
        double inputLongitude = 127.037033003036;

        DocumentDto documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build();

        when(pharmacySearchService.searchPharmacyDtoList()).thenReturn(pharmacyList);

        // When
        List<Direction> results = directionService.buildDirectionList(documentDto);

        // Then
        assertEquals(2, results.size());
        assertEquals("호수온누리약국", results.get(0).getTargetPharmacyName());
        assertEquals("돌곶이온누리약국", results.get(1).getTargetPharmacyName());
    }
}
