package com.example.pharmacyrecommendation

import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.GenericContainer
import spock.lang.Specification

@SpringBootTest
abstract class AbstractIntegrationContainerBaseTest extends Specification{

    // redis를 테스트에서 사용하려면 도커 이미지를 컨테이너에 넣어줘야 한다.
    static final GenericContainer MY_REDIS_CONTAINER

    static {
        MY_REDIS_CONTAINER = new GenericContainer<>("redis:6")
            .withExposedPorts(6379)
        // 컨테이너 시작
        MY_REDIS_CONTAINER.start()
        // 도커에서 expose 할 때 포트는 6379지만 호스트에서 포트는 충돌하지 않는 랜덤 포트가 정해진다.
        // 이 랜덤 포트를 스프링부트에게 알려줘야 통신할 수 있다.
        System.setProperty("spring.redis.host", MY_REDIS_CONTAINER.getHost())
        System.setProperty("spring.redis.port", MY_REDIS_CONTAINER.getMappedPort(6379).toString())


    }

}
