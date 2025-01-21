package com.example.pharmacyrecommendation.direction.controller

import com.example.pharmacyrecommendation.direction.service.DirectionService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class DirectionControllerTest extends Specification {

    private MockMvc mockMvc
    private DirectionService directionService = Mock()

    def setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(new DirectionController(directionService))
            .build()
    }

    def "GET /dir/{encodedId}"(){
        given:
        String encodedId = "r"

        String redirectURL = "https://map.kakao.com/link/map/pharmacy,38.11,128.11"

        when:
        directionService.findDirectionUrl(encodedId) >> redirectURL

        def result = mockMvc.perform(MockMvcRequestBuilders.get("/dir/{encodedId}", encodedId))

        then:
        result.andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl(redirectURL))
            .andDo(print())
    }

}
