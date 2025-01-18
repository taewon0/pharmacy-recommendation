package com.example.pharmacyrecommendation.api.service;

import com.example.pharmacyrecommendation.api.dto.KakaoAddressSearchApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAddressSearchService {

    private final RestTemplate restTemplate;
    private final KakaoUriBuilderService kakaoUriBuilderService;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    public KakaoAddressSearchApiResponse requestAddressSearch(String address){

        if(ObjectUtils.isEmpty(address)) return null;

        URI uri = kakaoUriBuilderService.buildUriByAddressSearch(address);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        /**
         * 카카오 api 호출
         * RestTemplate(동기) vs WebClient(비동기) 선택지 중 여기선 RestTemplate 사용.
         * 추후에 WebClient 로 변경을 고려.
         */

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoAddressSearchApiResponse.class).getBody();
    }

}
