package com.terrastation.sha;


import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class EntityControllerIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerIntegrationTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort //permet d'utiliser le port local du serveur, sinon une erreur "Connection refused"
    private int port;

    private static final String URL= "http://localhost:";//url du serveur REST. Cette URL peut être celle d'un serveur distant

    private String getURLWithPort(String uri) {
        return URL + port + uri;
    }

    // Les tests ici
}

