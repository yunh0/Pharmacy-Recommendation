package org.yunho.pharmacyrecommendation.pharmacy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.yunho.pharmacyrecommendation.pharmacy.cache.PharmacyRedisTemplateService;
import org.yunho.pharmacyrecommendation.pharmacy.dto.PharmacyDto;
import org.yunho.pharmacyrecommendation.pharmacy.entity.Pharmacy;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PharmacySearchServiceTest {

    @Mock
    private PharmacyRepositoryService pharmacyRepositoryService;

    @Mock
    private PharmacyRedisTemplateService pharmacyRedisTemplateService;

    @InjectMocks
    private PharmacySearchService pharmacySearchService;

    private List<Pharmacy> pharmacyList;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        pharmacyList = Arrays.asList(
                Pharmacy.builder()
                        .id(1L)
                        .pharmacyName("호수온누리약국")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build(),
                Pharmacy.builder()
                        .id(2L)
                        .pharmacyName("돌곶이온누리약국")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build()
        );
    }

    @Test
    public void testSearchPharmacyDtoListWhenRedisFails() {
        // Arrange
        when(pharmacyRedisTemplateService.findAll()).thenReturn(Arrays.asList());
        when(pharmacyRepositoryService.findAll()).thenReturn(pharmacyList);

        // Act
        List<PharmacyDto> result = pharmacySearchService.searchPharmacyDtoList();

        // Assert
        assertEquals(2, result.size());
    }
}
