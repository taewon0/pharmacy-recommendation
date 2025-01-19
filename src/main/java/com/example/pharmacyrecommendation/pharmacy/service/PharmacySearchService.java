package com.example.pharmacyrecommendation.pharmacy.service;

import com.example.pharmacyrecommendation.pharmacy.dto.PharmacyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacySearchService {

    private final PharmacyRepositoryService pharmacyRepositoryService;

    public List<PharmacyDto> searchPharmacyDtoList(){

        // redis

        // db
        return pharmacyRepositoryService.findAll()
                .stream()
                .map(PharmacyDto::toDto).toList();
                // collect(Collectors.toList()) 사용시 수정가능한 ArrayList 반환
                // toList() 는 수정불가능한 UnModifiableList 반환

    }

}
