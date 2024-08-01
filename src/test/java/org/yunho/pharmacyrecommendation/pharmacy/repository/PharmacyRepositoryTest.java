package org.yunho.pharmacyrecommendation.pharmacy.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yunho.pharmacyrecommendation.AbstractIntegrationContainerBaseTest;
import org.yunho.pharmacyrecommendation.pharmacy.entity.Pharmacy;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @BeforeEach
    public void setup() {
        pharmacyRepository.deleteAll();
    }

    @Test
    public void testPharmacyRepositorySave() {
        // given
        String address = "서울 특별시 성북구 종암동";
        String name = "은혜 약국";
        double latitude = 36.11;
        double longitude = 128.11;

        Pharmacy pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        // when
        Pharmacy result = pharmacyRepository.save(pharmacy);

        // then
        assertEquals(address, result.getPharmacyAddress());
        assertEquals(name, result.getPharmacyName());
        assertEquals(latitude, result.getLatitude());
        assertEquals(longitude, result.getLongitude());
    }

    @Test
    public void testPharmacyRepositorySaveAll() {
        // given
        String address = "서울 특별시 성북구 종암동";
        String name = "은혜 약국";
        double latitude = 36.11;
        double longitude = 128.11;

        Pharmacy pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        // when
        pharmacyRepository.saveAll(Collections.singletonList(pharmacy));
        Iterable<Pharmacy> result = pharmacyRepository.findAll();

        // then
        long count = result.spliterator().getExactSizeIfKnown(); // Count number of items
        assertEquals(1, count);
    }

    @Test
    void givenPharmacy_whenSaved_thenCreatedAndModifiedDatesShouldBeSet() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        String address = "서울 특별시 성북구 종암동";
        String name = "은혜 약국";

        Pharmacy pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .build();

        // When
        pharmacyRepository.save(pharmacy);
        List<Pharmacy> result = pharmacyRepository.findAll();

        // Then
        Pharmacy savedPharmacy = result.get(0);
        assertTrue(savedPharmacy.getCreatedDate().isAfter(now));
        assertTrue(savedPharmacy.getModifiedDate().isAfter(now));
    }
}
