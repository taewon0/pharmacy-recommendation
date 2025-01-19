package com.example.pharmacyrecommendation.pharmacy.dto;

import com.example.pharmacyrecommendation.pharmacy.entity.Pharmacy;

public record PharmacyDto(
        Long id,
        String pharmacyName,
        String pharmacyAddress,
        double x,
        double y
) {
    public PharmacyDto(Long id, String pharmacyName, String pharmacyAddress, double x, double y) {
        this.id = id;
        this.pharmacyName = pharmacyName;
        this.pharmacyAddress = pharmacyAddress;
        this.x = x;
        this.y = y;
    }

    public static PharmacyDto of(Long id, String pharmacyName, String pharmacyAddress, double x, double y) {
        return new PharmacyDto(id, pharmacyName, pharmacyAddress, x, y);
    }

    public static PharmacyDto toDto(Pharmacy entity){
        return PharmacyDto.of(
                entity.getId(),
                entity.getPharmacyName(),
                entity.getPharmacyAddress(),
                entity.getX(),
                entity.getY()
        );
    }

    public Pharmacy toEntity(){
        return Pharmacy.of(id, pharmacyName, pharmacyAddress, x, y);
    }

}
