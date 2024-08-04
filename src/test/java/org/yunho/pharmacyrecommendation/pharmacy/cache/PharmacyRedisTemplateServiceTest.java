package org.yunho.pharmacyrecommendation.pharmacy.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yunho.pharmacyrecommendation.pharmacy.dto.PharmacyDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PharmacyRedisTemplateServiceTest {

    @Autowired
    private PharmacyRedisTemplateService pharmacyRedisTemplateService;

    @BeforeEach
    public void setup() {
        List<PharmacyDto> dtos = pharmacyRedisTemplateService.findAll();
        for (PharmacyDto dto : dtos) {
            pharmacyRedisTemplateService.delete(dto.getId());
        }
    }

    @Test
    public void saveSuccess() {
        // Given
        String pharmacyName = "name";
        String pharmacyAddress = "address";
        PharmacyDto dto = PharmacyDto.builder()
                .id(1L)
                .pharmacyName(pharmacyName)
                .pharmacyAddress(pharmacyAddress)
                .build();

        // When
        pharmacyRedisTemplateService.save(dto);
        List<PharmacyDto> result = pharmacyRedisTemplateService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(pharmacyName, result.get(0).getPharmacyName());
        assertEquals(pharmacyAddress, result.get(0).getPharmacyAddress());
    }

    @Test
    public void saveFailure() {
        // Given
        PharmacyDto dto = PharmacyDto.builder().build();

        // When
        pharmacyRedisTemplateService.save(dto);
        List<PharmacyDto> result = pharmacyRedisTemplateService.findAll();

        // Then
        assertEquals(0, result.size());
    }

    @Test
    public void delete() {
        // Given
        String pharmacyName = "name";
        String pharmacyAddress = "address";
        PharmacyDto dto = PharmacyDto.builder()
                .id(1L)
                .pharmacyName(pharmacyName)
                .pharmacyAddress(pharmacyAddress)
                .build();

        // When
        pharmacyRedisTemplateService.save(dto);
        pharmacyRedisTemplateService.delete(dto.getId());
        List<PharmacyDto> result = pharmacyRedisTemplateService.findAll();

        // Then
        assertEquals(0, result.size());
    }
}

