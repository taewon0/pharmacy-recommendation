package com.example.pharmacyrecommendation.pharmacy.service

import com.example.pharmacyrecommendation.pharmacy.cache.PharmacyRedisTemplateService
import com.example.pharmacyrecommendation.pharmacy.dto.PharmacyDto
import com.example.pharmacyrecommendation.pharmacy.entity.Pharmacy
import org.testcontainers.shaded.com.google.common.collect.Lists
import spock.lang.Specification

class PharmacySearchServiceTest extends Specification {

    private PharmacySearchService pharmacySearchService

    private PharmacyRepositoryService pharmacyRepositoryService = Mock()
    private PharmacyRedisTemplateService pharmacyRedisTemplateService = Mock()

    private List<Pharmacy> pharmacyList;

    def setup(){
        pharmacySearchService = new PharmacySearchService(pharmacyRepositoryService, pharmacyRedisTemplateService)

        pharmacyList = Lists.newArrayList(
                Pharmacy.builder()
                .id(1L)
                .pharmacyName("하나로약국")
                .x(126.871952248872)
                .y(37.5513728079184)
                .build(),
                Pharmacy.builder()
                .id(2L)
                .pharmacyName("태평양약국")
                .x(126.863209505498)
                .y(37.5363950714321)
                .build()
        )

    }

    def "레디스 장애시 db를 이용하여 약국 데이터 조회"(){
        when:
        pharmacyRedisTemplateService.findAll() >> []
        pharmacyRepositoryService.findAll() >> pharmacyList

        def result = pharmacySearchService.searchPharmacyDtoList()

        then:
        result.size() == 2
    }

}
