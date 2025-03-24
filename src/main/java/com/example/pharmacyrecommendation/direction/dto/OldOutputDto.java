package com.example.pharmacyrecommendation.direction.dto;

import com.example.pharmacyrecommendation.direction.entity.Direction;
import com.example.pharmacyrecommendation.direction.service.Base62Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record OldOutputDto(
        String pharmacyName,
        String pharmacyAddress,
        String directionUrl,
        String roadViewUrl,
        String distance
) {
    public OldOutputDto(String pharmacyName, String pharmacyAddress, String directionUrl, String roadViewUrl, String distance) {
        this.pharmacyName = pharmacyName;
        this.pharmacyAddress = pharmacyAddress;
        this.directionUrl = directionUrl;
        this.roadViewUrl = roadViewUrl;
        this.distance = distance;
    }

    public static OldOutputDto of(String pharmacyName, String pharmacyAddress, String directionUrl, String roadViewUrl, String distance) {
        return new OldOutputDto(pharmacyName, pharmacyAddress, directionUrl, roadViewUrl, distance);
    }

    public static OldOutputDto of(String pharmacyName) {
        return new OldOutputDto(pharmacyName, null, null, null, null);
    }

    public static OldOutputDto toDto(Direction direction){

        Base62Service base62Service = new Base62Service();
        String encodedId = base62Service.encodeDirectionId(direction.getId());
        return OldOutputDto.of(
                direction.getTargetPharmacyName(),
                direction.getTargetAddress(),
                encodedId,
                encodedId,
                String.format("%.2f km",direction.getDistance())
        );
    }

}
