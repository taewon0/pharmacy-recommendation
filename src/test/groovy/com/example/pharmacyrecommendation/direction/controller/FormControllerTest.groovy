package com.example.pharmacyrecommendation.direction.controller

import com.example.pharmacyrecommendation.direction.dto.OutputDto
import com.example.pharmacyrecommendation.pharmacy.service.PharmacyRecommendationService
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class FormControllerTest extends Specification {

    private MockMvc mockMvc;
    private PharmacyRecommendationService pharmacyRecommendationService = Mock()
    private List<OutputDto> outputDtoList;

    def setup(){
        mockMvc = standaloneSetup(new FormController(pharmacyRecommendationService))
        .build()

        outputDtoList = new ArrayList<>()
        outputDtoList.addAll(
                OutputDto.builder().pharmacyName("pharmacy1").build(),
                OutputDto.builder().pharmacyName("pharmacy2").build()
        )
    }

    def "GET /"(){
        expect:
        // FormController의 "/" URI를 GET 방식으로 호출
        mockMvc.perform(get("/"))
            .andExpect(handler().handlerType(FormController.class))
            .andExpect(handler().methodName("main"))
            .andExpect(status().isOk())
            .andExpect(view().name("main"))
            .andDo(print())
    }

    def "POST /search"(){
        given:
        String inputAddress = "서울 강서구 공항대로 290"

        when:
        def resultActions = mockMvc.perform(post("/search")
                .param("address", inputAddress))

        then:
        1 * pharmacyRecommendationService.recommendPharmacyList(argument -> {
            assert argument == inputAddress
        }) >> outputDtoList

        resultActions
            .andExpect(status().isOk())
            .andExpect(view().name("output"))
            .andExpect(model().attributeExists("outputFormList"))
            .andExpect(model().attribute("outputFormList", outputDtoList))
            .andDo(print())

        // FormController의 "/search"를 POST 방식으로 호출

    }

}
