package com.example.pharmacyrecommendation.pharmacy.service;

import com.example.pharmacyrecommendation.api.dto.DocumentDto;
import com.example.pharmacyrecommendation.api.dto.KakaoAddressSearchApiResponse;
import com.example.pharmacyrecommendation.api.service.KakaoAddressSearchService;
import com.example.pharmacyrecommendation.direction.dto.OutputDto;
import com.example.pharmacyrecommendation.direction.entity.Direction;
import com.example.pharmacyrecommendation.direction.service.Base62Service;
import com.example.pharmacyrecommendation.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${pharmacy.recommendation.dir.base.url}")
    public String DIRECTION_BASE_URL;
    @Value("${pharmacy.recommendation.road.base.url}")
    public String ROAD_VIEW_BASE_URL;
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

        List<Direction> directionList = directionService.buildDirectionList(documentDto);
        //List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);
        Base62Service base62Service = new Base62Service();
        return directionService.saveAll(directionList)
                .stream().map(dto -> OutputDto.builder()
                        .pharmacyName(dto.getTargetPharmacyName())
                        .pharmacyAddress(dto.getTargetAddress())
                        .directionUrl(DIRECTION_BASE_URL + base62Service.encodeDirectionId(dto.getId()))
                        .roadViewUrl(ROAD_VIEW_BASE_URL + base62Service.encodeDirectionId(dto.getId()))
                        .distance(String.format("%.2f km", dto.getDistance()))
                        .build()).toList();
    }

}
