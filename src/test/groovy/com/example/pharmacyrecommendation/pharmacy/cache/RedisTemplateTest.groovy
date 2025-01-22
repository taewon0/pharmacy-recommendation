package com.example.pharmacyrecommendation.pharmacy.cache

import com.example.pharmacyrecommendation.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate

class RedisTemplateTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private RedisTemplate redisTemplate

    def "RedisTemplate String operations"(){
        given:
        def valueOperations = redisTemplate.opsForValue()
        def key = "stringKey"
        def value = "hello"

        when:
        valueOperations.set(key, value)

        then:
        def result = valueOperations.get(key)
        result == value
    }

}
