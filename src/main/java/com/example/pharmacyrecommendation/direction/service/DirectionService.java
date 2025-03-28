package com.example.pharmacyrecommendation.direction.service;

import com.example.pharmacyrecommendation.api.dto.DocumentDto;
import com.example.pharmacyrecommendation.api.service.KakaoCategorySearchService;
import com.example.pharmacyrecommendation.direction.entity.Direction;
import com.example.pharmacyrecommendation.direction.repository.DirectionRepository;
import com.example.pharmacyrecommendation.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 3; // 약국 추천 최대 개수
    private static final double RADIUS_CUS_KM = 2; // 고객 반경 범위 2km

    public static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/to/";
    public static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

    private final KakaoCategorySearchService kakaoCategorySearchService;
    private final PharmacySearchService pharmacySearchService;
    private final DirectionRepository directionRepository;
    private final Base62Service base62Service = new Base62Service();

    @Transactional
    public List<Direction> saveAll(List<Direction> directionList){
        if (CollectionUtils.isEmpty(directionList)) return Collections.emptyList();

        return directionRepository.saveAll(directionList);

    }

    public String findDirectionUrl(String encodedId){
        Long decodedId = base62Service.decodeDirectionId(encodedId);
        Direction direction = directionRepository.findById(decodedId).orElse(null);
        if (Objects.isNull(direction)) return "";

        String params = String.join(",",
                direction.getTargetPharmacyName(), String.valueOf(direction.getTargetY()), String.valueOf(direction.getTargetX()));

        return UriComponentsBuilder.fromUriString(DIRECTION_BASE_URL + params).toUriString();
    }

    public String findRoadViewUrl(String encodedId){
        Long decodedId = base62Service.decodeDirectionId(encodedId);
        Direction direction = directionRepository.findById(decodedId).orElse(null);
        if (Objects.isNull(direction)) return "";

        return ROAD_VIEW_BASE_URL + direction.getTargetY() + "," + direction.getTargetX();
    }

    public List<Direction> buildDirectionList(DocumentDto documentDto){

        if (Objects.isNull(documentDto)) return Collections.emptyList();

        return pharmacySearchService.searchPharmacyDtoList()
                .stream().map(pharmacyDto -> Direction.builder()
                        .inputAddress(documentDto.getAddressName())
                        .inputX(documentDto.getX())
                        .inputY(documentDto.getY())
                        .targetAddress(pharmacyDto.pharmacyAddress())
                        .targetPharmacyName(pharmacyDto.pharmacyName())
                        .targetX(pharmacyDto.x())
                        .targetY(pharmacyDto.y())
                        .distance(calculateDistanceByHaversine(documentDto.getX(), documentDto.getY(), pharmacyDto.x(), pharmacyDto.y()))
                        .build())
                .filter(direction -> direction.getDistance() <= RADIUS_CUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .toList();
    }

    public List<Direction> buildDirectionListByCategoryApi(DocumentDto documentDto){

        if (Objects.isNull(documentDto)) return Collections.emptyList();

        return kakaoCategorySearchService
                .requestPharmacyCategorySearch(documentDto.getX(), documentDto.getY(), RADIUS_CUS_KM)
                .getDocumentDtoList()
                .stream().map(resultDocumentDto ->
                        Direction.builder()
                                .inputAddress(documentDto.getAddressName())
                                .inputX(documentDto.getX())
                                .inputY(documentDto.getY())
                                .targetPharmacyName(resultDocumentDto.getPlaceName())
                                .targetAddress(resultDocumentDto.getAddressName())
                                .targetX(resultDocumentDto.getX())
                                .targetY(resultDocumentDto.getY())
                                .distance(resultDocumentDto.getDistance() * 0.001)
                                .build())
                .limit(MAX_SEARCH_COUNT)
                .toList();
    }

    // 구면 코사인 법칙
    private double calculateDistanceBySphericalLawOfCosine(double x1, double y1, double x2, double y2){
        x1 = Math.toRadians(x1);
        y1 = Math.toRadians(y1);
        x2 = Math.toRadians(x2);
        y2 = Math.toRadians(y2);

        double earthRadius = 6371;

        return earthRadius * Math.acos(Math.sin(y1) * Math.sin(y2) + Math.cos(y1) * Math.cos(y2) * Math.cos(x1 - x2));
    }

    // Haversine formula
    private double calculateDistanceByHaversine(double x1, double y1, double x2, double y2){
        double earthRadius = 6371; // 지구의 반지름 (킬로미터)

        x1 = Math.toRadians(x1);
        y1 = Math.toRadians(y1);
        x2 = Math.toRadians(x2);
        y2 = Math.toRadians(y2);

        double SqSinDeltaY = Math.pow(Math.sin(Math.abs(y2 - y1) / 2), 2);
        double SqSinDeltaX = Math.pow(Math.sin(Math.abs(x2 - x1) / 2), 2);

        double sqrt = Math.sqrt(SqSinDeltaY + Math.cos(y1) * Math.cos(y2) * SqSinDeltaX);

        return 2 * earthRadius * Math.asin(sqrt);
    }
}
