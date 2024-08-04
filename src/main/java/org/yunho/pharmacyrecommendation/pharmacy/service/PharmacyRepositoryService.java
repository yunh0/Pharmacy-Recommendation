package org.yunho.pharmacyrecommendation.pharmacy.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yunho.pharmacyrecommendation.pharmacy.dto.PharmacyDto;
import org.yunho.pharmacyrecommendation.pharmacy.entity.Pharmacy;
import org.yunho.pharmacyrecommendation.pharmacy.repository.PharmacyRepository;
import org.yunho.pharmacyrecommendation.util.CsvUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRepositoryService {
    private final PharmacyRepository pharmacyRepository;

    //DB에 처음 약국 데이터 생성 시 사용
//    @PostConstruct
//    public void init() {
//        List<PharmacyDto> pharmacyDtoList = CsvUtils.convertToPharmacyDtoList();
//        saveAll(pharmacyDtoList);
//    }

    public void saveAll(List<PharmacyDto> pharmacyDtoList) {
        List<Pharmacy> pharmacies = pharmacyDtoList.stream().map(dto -> Pharmacy.builder()
                .pharmacyName(dto.getPharmacyName())
                .pharmacyAddress(dto.getPharmacyAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build()).collect(Collectors.toList());
        pharmacyRepository.saveAll(pharmacies);
    }

    @Transactional
    public void updateAddress(Long id, String address) {
        Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

        if(Objects.isNull(entity)) {
            log.error("[PharmacyRepositoryService updateAddress] not found id: {}", id);
            return;
        }

        entity.changePharmacyAddress(address);
    }

    // for test
    public void updateAddressWithoutTransaction(Long id, String address) {
        Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

        if(Objects.isNull(entity)) {
            log.error("[PharmacyRepositoryService updateAddress] not found id: {}", id);
            return;
        }

        entity.changePharmacyAddress(address);
    }

    @Transactional(readOnly = true)
    public List<Pharmacy> findAll(){
        return pharmacyRepository.findAll();
    }
}
