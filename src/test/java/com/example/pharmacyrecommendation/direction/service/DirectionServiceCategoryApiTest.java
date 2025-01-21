package com.example.pharmacyrecommendation.direction.service;

import com.example.pharmacyrecommendation.api.dto.DocumentDto;
import com.example.pharmacyrecommendation.direction.entity.Direction;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Disabled
@SpringBootTest
class DirectionServiceCategoryApiTest {

    @Autowired
    private DirectionService directionService;

    @Test
    void buildDirectionListByCategoryApi_isReturnedCorrectly() {
        //given
        DocumentDto documentDto = DocumentDto.builder()
                .x(126.874890556801)
                .y(37.5533141837481)
                .addressName("서울 강서구 양천로75길 57")
                .build();
        //when
        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        //then

        // 리턴 결과 3개인지 확인
        assertThat(directionList.size()).isEqualTo(3);
        // 리턴 결과 정렬 확인
        assertThat(directionList.get(0).getDistance()).isLessThan(directionList.get(1).getDistance());
        // 리턴 결과 반경 2km 이내인지 확인
        assertThat(directionList).allMatch(direction -> direction.getDistance() <= 2);

    }
}