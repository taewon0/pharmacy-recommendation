package com.example.pharmacyrecommendation.pharmacy.service;

import com.example.pharmacyrecommendation.api.dto.DocumentDto;
import com.example.pharmacyrecommendation.api.dto.KakaoAddressSearchApiResponse;
import com.example.pharmacyrecommendation.api.service.KakaoAddressSearchService;
import com.example.pharmacyrecommendation.direction.dto.OutputDto;
import com.example.pharmacyrecommendation.direction.entity.Direction;
import com.example.pharmacyrecommendation.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public List<OutputDto> recommendPharmacyList(String address){

        KakaoAddressSearchApiResponse response = kakaoAddressSearchService.requestAddressSearch(address);

        if (Objects.isNull(response)) {
            log.error("[PharmacyRecommendationService][recommendPharmacyList] Null returned. address: {}", address);
            return Collections.emptyList();
        }

        if (CollectionUtils.isEmpty(response.getDocumentDtoList())){
            log.error("[PharmacyRecommendationService][recommendPharmacyList] Empty list returned. address: {}", address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = response.getDocumentDtoList().get(0);

        //List<Direction> directionList = directionService.buildDirectionList(documentDto);
        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        return directionService.saveAll(directionList)
                .stream().map(OutputDto::toDto).toList();

    }

}
