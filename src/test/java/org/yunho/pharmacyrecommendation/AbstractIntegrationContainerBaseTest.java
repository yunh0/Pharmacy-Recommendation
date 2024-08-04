package org.yunho.pharmacyrecommendation;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public abstract class AbstractIntegrationContainerBaseTest {

    static final GenericContainer<?> MY_REDIS_CONTAINER = new GenericContainer<>("redis:6")
            .withExposedPorts(6379);

    @BeforeAll
    static void setUp() {
        MY_REDIS_CONTAINER.start();
        System.setProperty("spring.redis.host", MY_REDIS_CONTAINER.getHost());
        System.setProperty("spring.redis.port", MY_REDIS_CONTAINER.getMappedPort(6379).toString());
    }

    @AfterAll
    static void tearDown() {
        MY_REDIS_CONTAINER.stop();
    }
}

