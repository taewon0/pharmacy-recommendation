package com.example.pharmacyrecommendation.pharmacy.service

import com.example.pharmacyrecommendation.AbstractIntegrationContainerBaseTest
import com.example.pharmacyrecommendation.pharmacy.entity.Pharmacy
import com.example.pharmacyrecommendation.pharmacy.repository.PharmacyRepository
import org.junit.jupiter.api.Disabled
import org.springframework.beans.factory.annotation.Autowired

@Disabled
class PharmacyRepositoryServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepositoryService pharmacyRepositoryService;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    def setup(){
        pharmacyRepository.deleteAll()
    }

    def "PharmacyRepository update - dirty checking success"(){
        given:
        String inputAddress = "서울시 강서구 염창동"
        String modifiedAddress = "서울시 광진구 군자동"
        String name = "하나로 약국"

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(inputAddress)
                .pharmacyName(name)
                .build()

        when:
        def entity = pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddress(entity.getId(), modifiedAddress)

        def result = pharmacyRepository.findAll()

        then:
        result.get(0).getPharmacyAddress() == modifiedAddress
    }

    def "PharmacyRepository update - dirty checking fail"(){
        given:
        String inputAddress = "서울시 강서구 염창동"
        String modifiedAddress = "서울시 광진구 군자동"
        String name = "하나로 약국"

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(inputAddress)
                .pharmacyName(name)
                .build()

        when:
        def entity = pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddressWithoutTransaction(entity.getId(), modifiedAddress)

        def result = pharmacyRepository.findAll()

        then:
        result.get(0).getPharmacyAddress() == inputAddress
    }

    def "self invocation"(){
        given:
        String inputAddress = "서울시 강서구 염창동"
        String modifiedAddress = "서울시 광진구 군자동"
        String name = "하나로 약국"
        double x = 128.11
        double y = 36.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(inputAddress)
                .pharmacyName(name)
                .x(x)
                .y(y)
                .build()

        when:
        pharmacyRepositoryService.bar(Arrays.asList(pharmacy))

        then:
        def e = thrown(RuntimeException.class)
        def result = pharmacyRepositoryService.findAll()
        result.size() == 1 // 트랜잭션이 적용되지 않는다(롤백 X)

    }

}
