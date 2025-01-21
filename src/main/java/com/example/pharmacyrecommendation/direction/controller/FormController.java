package com.example.pharmacyrecommendation.direction.controller;

import com.example.pharmacyrecommendation.direction.dto.InputDto;
import com.example.pharmacyrecommendation.pharmacy.service.PharmacyRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class FormController {

    private final PharmacyRecommendationService pharmacyRecommendationService;

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @PostMapping("/search")
    public String postDirection(@ModelAttribute InputDto inputDto, Model model){
        model.addAttribute("outputFormList",
                pharmacyRecommendationService.recommendPharmacyList(inputDto.getAddress()));

        return "output";
    }

}
