package com.example.pharmacyrecommendation.direction.service

import com.example.pharmacyrecommendation.api.dto.DocumentDto
import com.example.pharmacyrecommendation.api.service.KakaoCategorySearchService
import com.example.pharmacyrecommendation.direction.repository.DirectionRepository
import com.example.pharmacyrecommendation.pharmacy.dto.PharmacyDto
import com.example.pharmacyrecommendation.pharmacy.service.PharmacySearchService
import spock.lang.Specification

class DirectionServiceTest extends Specification {

    private PharmacySearchService pharmacySearchService = Mock()
    private DirectionRepository directionRepository = Mock()
    private KakaoCategorySearchService kakaoCategorySearchService = Mock()

    private DirectionService directionService = new DirectionService(
            kakaoCategorySearchService, pharmacySearchService, directionRepository
    )

    private List<PharmacyDto> pharmacyDtoList

    def setup(){
        pharmacyDtoList = new ArrayList<>()
        pharmacyDtoList.addAll(
                PharmacyDto.of(
                        1L,
                        "하나로약국",
                        "서울특별시 강서구 양천로 677",
                        126.871952248872,
                        37.5513728079184
                ),
                PharmacyDto.of(
                        2L,
                        "태평양약국",
                        "서욽특별시 강서구 등촌로 71",
                        126.863209505498,
                        37.5363950714321
                )
        )
    }

    def "buildDirectionList - 결과값이 거리순으로 정렬 되는지 확인"(){
        given:
        def addressName = "서울 강서구 양천로75길 57"
        double inputX = 126.874890556801
        double inputY = 37.5533141837481

        def documentDto = DocumentDto.builder()
            .addressName(addressName)
            .x(inputX)
            .y(inputY)
            .build()

        when:
        pharmacySearchService.searchPharmacyDtoList() >> pharmacyDtoList
        def results = directionService.buildDirectionList(documentDto)

        then:
        // 하나는 반경 밖에 있음
        results.size() == 1
        results.get(0).targetPharmacyName == "하나로약국"
        println results.get(0).distance
    }

    def "buildDirectionList - 정해진 반경(2km) 이내로 검색이 되는지 확인"(){

        given:
        pharmacyDtoList.add(
                PharmacyDto.of(
                        3L,
                        "세종대학교",
                        "서울 광진구 능동로 209",
                        127.07318364213,
                        37.5516093089619
                )
        )
        def addressName = "서울 강서구 양천로75길 57"
        double inputX = 126.874890556801
        double inputY = 37.5533141837481

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .x(inputX)
                .y(inputY)
                .build()

        when:
        pharmacySearchService.searchPharmacyDtoList() >> pharmacyDtoList
        def results = directionService.buildDirectionList(documentDto)

        then:
        results.size() == 1
        results.get(0).targetPharmacyName == "하나로약국"
        println results.get(0).distance
    }

}
