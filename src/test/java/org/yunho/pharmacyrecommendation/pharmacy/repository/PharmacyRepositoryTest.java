package org.yunho.pharmacyrecommendation.pharmacy.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yunho.pharmacyrecommendation.AbstractIntegrationContainerBaseTest;
import org.yunho.pharmacyrecommendation.pharmacy.entity.Pharmacy;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
