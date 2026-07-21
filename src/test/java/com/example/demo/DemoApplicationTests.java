package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class DemoApplicationTests {

    @LocalServerPort
    private int port;

    @Test
    void contextLoads() {
        // Sanity check that the Spring context boots correctly
    }

    @Test
    void statusEndpointReturnsOk(@org.springframework.beans.factory.annotation.Autowired TestRestTemplate rest) {
        String body = rest.getForObject("http://localhost:" + port + "/api/status", String.class);
        assertThat(body).contains("\"status\":\"ok\"");
    }
}
