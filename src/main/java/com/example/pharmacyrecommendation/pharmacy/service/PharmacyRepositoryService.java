package com.example.pharmacyrecommendation.pharmacy.service;

import com.example.pharmacyrecommendation.pharmacy.entity.Pharmacy;
import com.example.pharmacyrecommendation.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRepositoryService {
    private final PharmacyRepository pharmacyRepository;

    // 아래 두 메소드는 Transactional 사용시 주의할 점에 대한 실습.
    public void bar(List<Pharmacy> pharmacyList){
        log.info("bar CurrentTransactionName : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        foo(pharmacyList);
    }

    @Transactional
    public void foo(List<Pharmacy> pharmacyList) {
        log.info("foo CurrentTransactionName : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        pharmacyList.forEach(pharmacy -> {
            pharmacyRepository.save(pharmacy);
            throw new RuntimeException("error");
        });

    }


    // 아래 두 메소드는 jpa dirty checking 실습.
    @Transactional
    public void updateAddress(Long id, String address){
        Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

        if (Objects.isNull(entity)){
            log.error("[PharmacyRepositoryService][updateAddress] id not found : {}", id);
            return;
        }

        entity.changePharmacyAddress(address);

    }

    public void updateAddressWithoutTransaction(Long id, String address){
        Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

        if (Objects.isNull(entity)){
            log.error("[PharmacyRepositoryService][updateAddress] id not found : {}", id);
            return;
        }

        entity.changePharmacyAddress(address);

    }

    @Transactional(readOnly = true)
    public List<Pharmacy> findAll(){
        return pharmacyRepository.findAll();
    }

}
