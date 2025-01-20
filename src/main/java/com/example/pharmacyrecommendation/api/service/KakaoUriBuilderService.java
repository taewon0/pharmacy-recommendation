package com.example.pharmacyrecommendation.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {

    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    private static final String KAKAO_LOCAL_CATEGORY_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/category.json";


    public URI buildUriByAddressSearch(String address){
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        builder.queryParam("query", address);

        URI uri = builder.build().encode().toUri();
        //log.info("[KakaoUriBuilderService][buildUriByAddressSearch] address: {}, uri: {}", address, uri);

        return uri;
    }

    public URI builderUriByCategorySearch(double x, double y, double radius, String category){

        double meterRadius = radius * 1000;

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(KAKAO_LOCAL_CATEGORY_SEARCH_URL);
        builder.queryParam("category_group_code", category);
        builder.queryParam("x", x);
        builder.queryParam("y", y);
        builder.queryParam("radius", meterRadius);
        builder.queryParam("sort", "distance");

        URI uri = builder.build().encode().toUri();
        //log.info("[KakaoUriBuilderService][builderUriByCategorySearch] uri: {}", uri);

        return uri;

    }

}
