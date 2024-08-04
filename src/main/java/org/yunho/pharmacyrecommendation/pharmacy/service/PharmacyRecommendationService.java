package org.yunho.pharmacyrecommendation.pharmacy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.yunho.pharmacyrecommendation.api.dto.DocumentDto;
import org.yunho.pharmacyrecommendation.api.dto.KakaoApiResponseDto;
import org.yunho.pharmacyrecommendation.api.service.KakaoAddressSearchService;
import org.yunho.pharmacyrecommendation.direction.entity.Direction;
import org.yunho.pharmacyrecommendation.direction.service.DirectionService;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public void recommendPharmacyList(String address) {

        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[PharmacyRecommendationService recommendPharmacyList fail] Input address: {}", address);
            return;
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

        List<Direction> directionList = directionService.buildDirectionList(documentDto);

        directionService.saveAll(directionList);

    }
}
