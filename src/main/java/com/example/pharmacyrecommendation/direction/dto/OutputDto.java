package com.example.pharmacyrecommendation.direction.dto;

import com.example.pharmacyrecommendation.direction.entity.Direction;
import com.example.pharmacyrecommendation.direction.service.Base62Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record OutputDto(
        String pharmacyName,
        String pharmacyAddress,
        String directionUrl,
        String roadViewUrl,
        String distance
) {

    public static String DIRECTION_BASE_URL = "http://localhost:8080/dir/";
    public static String ROAD_VIEW_BASE_URL = "http://localhost:8080/road/";

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

        Base62Service base62Service = new Base62Service();
        String encodedId = base62Service.encodeDirectionId(direction.getId());

        return OutputDto.of(
                direction.getTargetPharmacyName(),
                direction.getTargetAddress(),
                DIRECTION_BASE_URL + encodedId,
                ROAD_VIEW_BASE_URL + encodedId,
                String.format("%.2f km",direction.getDistance())
        );
    }

}
