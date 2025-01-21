package com.example.pharmacyrecommendation.direction.dto;


import com.example.pharmacyrecommendation.direction.entity.Direction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
public record OutputDto(
        String pharmacyName,
        String pharmacyAddress,
        String directionUrl,
        String roadViewUrl,
        String distance
) {

    public static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/to/";
    public static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

    public OutputDto(String pharmacyName, String pharmacyAddress, String directionUrl, String roadViewUrl, String distance) {
        this.pharmacyName = pharmacyName;
        this.pharmacyAddress = pharmacyAddress;
        this.directionUrl = directionUrl;
        this.roadViewUrl = roadViewUrl;
        this.distance = distance;
    }

    public static OutputDto of(String pharmacyName, String pharmacyAddress, String directionUrl, String roadViewUrl, String distance) {
        return new OutputDto(pharmacyName, pharmacyAddress, directionUrl, roadViewUrl, distance);
    }

    public static OutputDto of(String pharmacyName) {
        return new OutputDto(pharmacyName, null, null, null, null);
    }

    public static OutputDto toDto(Direction direction){

        String params = String.join(",",
                direction.getTargetPharmacyName(), String.valueOf(direction.getTargetY()), String.valueOf(direction.getTargetX()));

        String directionUrl = UriComponentsBuilder.fromUriString(DIRECTION_BASE_URL + params).toUriString();
        log.info("direction url : {}", directionUrl);

        return OutputDto.of(
                direction.getTargetPharmacyName(),
                direction.getTargetAddress(),
                directionUrl,
                ROAD_VIEW_BASE_URL + direction.getTargetY() + "," + direction.getTargetX(),
                String.format("%.2f km",direction.getDistance())
        );
    }

}
