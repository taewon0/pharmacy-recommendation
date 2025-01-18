package com.example.pharmacyrecommendation.api.service

import com.example.pharmacyrecommendation.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    def "requestAddressSearch - address 파라미터 값이 null이면, null을 리턴한다."(){
        given:
        String address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null

    }

    def "requestAddressSearch - address 파라미터 값이 valid하면, meta와 document[]를 반환한다."(){
        given:
        String address = "서울 강서구 공항대로 519"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentDtoList.size() > 0
        result.metaDto.totalCount > 0
        result.documentDtoList.get(0) != null

    }

}
