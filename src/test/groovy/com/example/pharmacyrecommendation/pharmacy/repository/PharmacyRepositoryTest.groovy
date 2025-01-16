package com.example.pharmacyrecommendation.pharmacy.repository

import com.example.pharmacyrecommendation.AbstractIntegrationContainerBaseTest
import com.example.pharmacyrecommendation.PharmacyRecommendationApplication
import com.example.pharmacyrecommendation.pharmacy.entity.Pharmacy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = PharmacyRecommendationApplication.class)
class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepository pharmacyRepository;

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
}
