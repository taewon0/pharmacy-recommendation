package com.example.pharmacyrecommendation.pharmacy.controller;

import com.example.pharmacyrecommendation.pharmacy.cache.PharmacyRedisTemplateService;
import com.example.pharmacyrecommendation.pharmacy.dto.PharmacyDto;
import com.example.pharmacyrecommendation.pharmacy.service.PharmacyRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PharmacyController {

    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    // 데이터 초기 셋팅을 위한 임시 메서드
    @GetMapping("/redis/save")
    public String save(){

        List<PharmacyDto> pharmacyDtoList = pharmacyRepositoryService.findAll()
                .stream().map(PharmacyDto::toDto).toList();

        pharmacyDtoList.forEach(pharmacyRedisTemplateService::save);

        return "success";
    }

}
