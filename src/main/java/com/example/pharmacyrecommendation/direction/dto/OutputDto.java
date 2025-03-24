package com.example.pharmacyrecommendation.direction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OutputDto {
    String pharmacyName;
    String pharmacyAddress;
    String directionUrl;
    String roadViewUrl;
    String distance;
}
