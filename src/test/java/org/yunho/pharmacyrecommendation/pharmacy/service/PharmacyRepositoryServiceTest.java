package org.yunho.pharmacyrecommendation.pharmacy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.yunho.pharmacyrecommendation.AbstractIntegrationContainerBaseTest;
import org.yunho.pharmacyrecommendation.pharmacy.entity.Pharmacy;
import org.yunho.pharmacyrecommendation.pharmacy.repository.PharmacyRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PharmacyRepositoryServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepositoryService pharmacyRepositoryService;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @BeforeEach
    void setup() {
        pharmacyRepository.deleteAll();
    }

    @Test
    void givenPharmacy_whenUpdateAddress_thenAddressShouldBeUpdated() {
        // Given
        String inputAddress = "서울 특별시 성북구 종암동";
        String modifiedAddress = "서울 광진구 구의동";
        String name = "은혜 약국";

        Pharmacy pharmacy = Pharmacy.builder()
                .pharmacyAddress(inputAddress)
                .pharmacyName(name)
                .build();

        // When
        Pharmacy entity = pharmacyRepository.save(pharmacy);
        pharmacyRepositoryService.updateAddress(entity.getId(), modifiedAddress);

        var result = pharmacyRepository.findAll();

        // Then
        assertEquals(modifiedAddress, result.get(0).getPharmacyAddress());
    }

    @Test
    void givenPharmacy_whenUpdateAddressWithoutTransaction_thenAddressShouldNotBeUpdated() {
        // Given
        String inputAddress = "서울 특별시 성북구 종암동";
        String modifiedAddress = "서울 광진구 구의동";
        String name = "은혜 약국";

        Pharmacy pharmacy = Pharmacy.builder()
                .pharmacyAddress(inputAddress)
                .pharmacyName(name)
                .build();

        // When
        Pharmacy entity = pharmacyRepository.save(pharmacy);
        pharmacyRepositoryService.updateAddressWithoutTransaction(entity.getId(), modifiedAddress);

        var result = pharmacyRepository.findAll();

        // Then
        assertEquals(inputAddress, result.get(0).getPharmacyAddress());
    }
}
