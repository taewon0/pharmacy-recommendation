package com.example.pharmacyrecommendation.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoAddressSearchApiResponse {

    private MetaDto metaDto;

    private List<DocumentDto> documentDtoList;
}
