package com.example.pharmacyrecommendation.api.service

import com.example.pharmacyrecommendation.AbstractIntegrationContainerBaseTest
import org.junit.jupiter.api.Disabled
import org.springframework.beans.factory.annotation.Autowired

@Disabled
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

    def "정상적인 주소를 입력받을 경우, 정상적으로 위도 경도로 변환된다."(){
        given:
        boolean actualResult = false

        when:
        def searchResult = kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        if (searchResult == null) actualResult = false
        else actualResult = searchResult.getDocumentDtoList().size() > 0

        actualResult == expectedResult
        println actualResult

        where:
        inputAddress                        | expectedResult
        "서울 특별시 성북구 종암동"             | true
        "서울 성북구 종암동 91"                | true
        "서울 대학로"                         | true
        "서울 성북구 종암동 잘못된 주소"         | false
        "광진구 구의동 251-45"                 | true
        "광진구 구의동 251-455555"             | false
        ""                                   | false
    }

}
