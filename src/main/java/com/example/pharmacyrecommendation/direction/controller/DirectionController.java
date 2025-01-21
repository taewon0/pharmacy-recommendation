package com.example.pharmacyrecommendation.direction.controller;

import com.example.pharmacyrecommendation.direction.entity.Direction;
import com.example.pharmacyrecommendation.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Slf4j
@Controller
public class DirectionController {

    private final DirectionService directionService;

    @GetMapping("/dir/{encodedId}")
    public String searchDirection(@PathVariable("encodedId") String encodedID){
        return "redirect:" + directionService.findDirectionUrl(encodedID);
    }

    @GetMapping("/road/{encodedId}")
    public String searchRoadView(@PathVariable("encodedId") String encodedID){
        return "redirect:" +  directionService.findRoadViewUrl(encodedID);
    }

}
