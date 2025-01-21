package com.example.pharmacyrecommendation.api.service;

import com.example.pharmacyrecommendation.api.dto.KakaoAddressSearchApiResponse;
import com.example.pharmacyrecommendation.pharmacy.entity.Pharmacy;
import com.example.pharmacyrecommendation.pharmacy.repository.PharmacyRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@Disabled
@SpringBootTest
class KakaoAddressSearchServiceDataImportTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @DisplayName("모든 약국 데이터의 주소가 카카오 api를 통해 조회 가능한지 확인한다.")
    @Test
    void getDataAndInsertData() {
        List<Pharmacy> list = pharmacyRepository.findAll();
        int i = 0;
        for (Pharmacy pharmacy : list) {
            KakaoAddressSearchApiResponse response = kakaoAddressSearchService.requestAddressSearch(pharmacy.getPharmacyAddress());

            if (response.getDocumentDtoList().size() < 1 || response.getDocumentDtoList().size() > 1){
                i++;
                System.out.println(pharmacy.getPharmacyName() + " " + pharmacy.getPharmacyAddress());
            }

        }
        System.out.println(i);
    }

    @DisplayName("좌표값을 얻어 db에 저장한다.")
    @Test
    void insertCoordinate() {
        List<Pharmacy> list = pharmacyRepository.findAll();
        int i = 1;
        for (Pharmacy pharmacy : list) {
            KakaoAddressSearchApiResponse response = kakaoAddressSearchService.requestAddressSearch(pharmacy.getPharmacyAddress());
            double x = response.getDocumentDtoList().get(0).getX();
            double y = response.getDocumentDtoList().get(0).getY();

            pharmacy.setCoordinate(x, y);

            pharmacyRepository.save(pharmacy);
        }

    }

}
