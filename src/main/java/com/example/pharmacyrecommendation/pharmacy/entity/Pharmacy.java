package com.example.pharmacyrecommendation.pharmacy.entity;

import com.example.pharmacyrecommendation.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "pharmacy")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacy extends BaseTimeEntity {

    // jpa로 save를 할 때, pk와 타입으로 영속성 컨텍스트에 저장되어야 하는데,
    // 예외적으로 id를 db에서 정해주는 경우 db에 먼저 저장 후 영속성 컨텍스트에 저장된다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pharmacyName;
    private String pharmacyAddress;
    private double x;
    private double y;

    public void setCoordinate(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void changePharmacyAddress(String address){
        this.pharmacyAddress = address;
    }

}
