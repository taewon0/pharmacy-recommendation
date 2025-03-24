package com.example.pharmacyrecommendation.pharmacy.repository

import com.example.pharmacyrecommendation.AbstractIntegrationContainerBaseTest
import com.example.pharmacyrecommendation.PharmacyRecommendationApplication
import com.example.pharmacyrecommendation.pharmacy.entity.Pharmacy
import org.junit.jupiter.api.Disabled
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import java.time.LocalDateTime

@Disabled
@ContextConfiguration(classes = PharmacyRecommendationApplication.class)
class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    def setup(){
        pharmacyRepository.deleteAll()
    }

    def "PharmacyRepository save"(){
        given:
        String address = "서울특별시 강서구 등촌동"
        String name = "하나로 약국"
        double x = 128.11
        double y = 36.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .x(x)
                .y(y)
                .build()

        when:
        def result = pharmacyRepository.save(pharmacy)

        then:
        result.getPharmacyAddress() == address
        result.getPharmacyName() == name
        result.getX() == x
        result.getY() == y

    }

    def "PharmacyRepository save All"(){
        given:
        String address = "서울특별시 강서구 등촌동"
        String name = "하나로 약국"
        double x = 128.11
        double y = 36.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .x(x)
                .y(y)
                .build()

        when:
        pharmacyRepository.saveAll(Arrays.asList(pharmacy))
        def result = pharmacyRepository.findAll()

        then:
        result.size() == 1

    }

    def "BaseTimeEntity 등록"(){

        given:
        LocalDateTime now = LocalDateTime.now()
        String address = "서울특별시 강서구 등촌동"
        String name = "하나로 약국"
        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .build()

        when:
        pharmacyRepository.save(pharmacy)
        def result = pharmacyRepository.findAll()

        then:
        result.get(0).getCreatedDate().isAfter(now)
        result.get(0).getModifiedDate().isAfter(now)

    }

}
