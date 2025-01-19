package com.example.pharmacyrecommendation.direction.entity;

import com.example.pharmacyrecommendation.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "direction")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Direction extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 고객
    private String inputAddress;
    private double inputX;
    private double inputY;

    // 약국
    private String targetPharmacyName;
    private double targetX;
    private double targetY;
    private String targetAddress;

    // 고객 주소와 약국 주소 사이의 거리
    private double distance;

}
