package org.yunho.pharmacyrecommendation.pharmacy.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yunho.pharmacyrecommendation.pharmacy.cache.PharmacyRedisTemplateService;
import org.yunho.pharmacyrecommendation.pharmacy.dto.PharmacyDto;
import org.yunho.pharmacyrecommendation.pharmacy.service.PharmacyRepositoryService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    // 데이터 초기 셋팅을 위한 임시 메서드
    @GetMapping("/redis/save")
    public String save() {

        List<PharmacyDto> pharmacyDtoList = pharmacyRepositoryService.findAll()
                .stream().map(pharmacy -> PharmacyDto.builder()
                        .id(pharmacy.getId())
                        .pharmacyName(pharmacy.getPharmacyName())
                        .pharmacyAddress(pharmacy.getPharmacyAddress())
                        .latitude(pharmacy.getLatitude())
                        .longitude(pharmacy.getLongitude())
                        .build())
                .collect(Collectors.toList());

        pharmacyDtoList.forEach(pharmacyRedisTemplateService::save);

        return "success";
    }
}
