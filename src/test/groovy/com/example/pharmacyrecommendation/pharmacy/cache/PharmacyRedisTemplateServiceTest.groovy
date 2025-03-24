package com.example.pharmacyrecommendation.pharmacy.cache

import com.example.pharmacyrecommendation.AbstractIntegrationContainerBaseTest
import com.example.pharmacyrecommendation.pharmacy.dto.PharmacyDto
import org.junit.jupiter.api.Disabled
import org.springframework.beans.factory.annotation.Autowired

@Disabled
class PharmacyRedisTemplateServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRedisTemplateService pharmacyRedisTemplateService;

    def setup(){
        pharmacyRedisTemplateService.findAll()
        .forEach(dto -> {
            pharmacyRedisTemplateService.delete(dto.id())
        })
    }

    def "save success"(){
        given:
        String pharmacyName = "name"
        String pharmacyAddress = "address"
        PharmacyDto dto = PharmacyDto.of(
                1L, pharmacyName, pharmacyAddress, 0, 0
        )

        when:
        pharmacyRedisTemplateService.save(dto)
        List<PharmacyDto> result = pharmacyRedisTemplateService.findAll()

        then:
        result.size() == 1
        result.get(0).id() == 1L
        result.get(0).pharmacyAddress() == "address"
        result.get(0).pharmacyName() == "name"
    }

    def "save fail"(){
        given:
        PharmacyDto dto = PharmacyDto.of(
                null, null, null, 0, 0
        )

        when:
        pharmacyRedisTemplateService.save(dto)
        List<PharmacyDto> result = pharmacyRedisTemplateService.findAll()

        then:
        result.size() == 0
    }

    def "delete"(){
        given:
        String pharmacyName = "name"
        String pharmacyAddress = "address"
        PharmacyDto dto = PharmacyDto.of(
                1L, pharmacyName, pharmacyAddress, 0, 0
        )

        when:
        pharmacyRedisTemplateService.save(dto)
        pharmacyRedisTemplateService.delete(dto.id())
        def result = pharmacyRedisTemplateService.findAll()

        then:
        result.size() == 0
    }

}
