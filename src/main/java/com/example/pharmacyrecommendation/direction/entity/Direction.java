package com.example.pharmacyrecommendation.direction.entity;

import com.example.pharmacyrecommendation.BaseTimeEntity;
import jakarta.persistence.*;
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
    @Column(name = "input_x")
    private double inputX;
    @Column(name = "input_y")
    private double inputY;

    // 약국
    private String targetPharmacyName;
    @Column(name = "target_x")
    private double targetX;
    @Column(name = "target_y")
    private double targetY;
    private String targetAddress;

    // 고객 주소와 약국 주소 사이의 거리
    private double distance;

}
