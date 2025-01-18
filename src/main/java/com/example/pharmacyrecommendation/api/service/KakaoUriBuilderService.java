package com.example.pharmacyrecommendation.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {

    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    public URI buildUriByAddressSearch(String address){
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        builder.queryParam("query", address);

        URI uri = builder.build().encode().toUri();
        //log.info("[KakaoUriBuilderService][buildUriByAddressSearch] address: {}, uri: {}", address, uri);

        return uri;
    }

}
