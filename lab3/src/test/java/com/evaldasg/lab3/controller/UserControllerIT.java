package com.evaldasg.lab3.controller;

import com.evaldasg.lab3.Lab3Application;
import com.evaldasg.lab3.entity.User;
import com.evaldasg.lab3.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(classes = Lab3Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class UserControllerIT {
    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserService userService;

    private final String requestMapping = "/user/";
    private User user;

    @BeforeEach
    void setUp() {
        user = userService.findAll().get(0);
    }

    @Test
    void test() {
        System.out.println("PORT=" + port);
    }

    @Test
    @Order(1)
    void testRetrieveAllUsers() {
        String expected = "[{\"id\":1,\"name\":\"Kestas\",\"surname\":\"Kestauskas\",\"phoneNumber\":\"+37063123823\",\"email\":\"kkestas@gmail.com\",\"address\":\"VILNIUS\",\"password\":\"qwasDD_qw123\"},{\"id\":2,\"name\":\"Testas\",\"surname\":\"Tests\",\"phoneNumber\":\"+37063123823\",\"email\":\"ttestas@gmail.com\",\"address\":\"VILNIUS\",\"password\":\"ads1sw_Qwed1\"},{\"id\":3,\"name\":\"Petras\",\"surname\":\"Petrauskas\",\"phoneNumber\":\"+37063123823\",\"email\":\"ppetrauskas@gmail.com\",\"address\":\"VILNIUS\",\"password\":\"asd_d213aSqweasd\"},{\"id\":4,\"name\":\"Jonas\",\"surname\":\"Jonauskas\",\"phoneNumber\":\"+37063123823\",\"email\":\"jjonauskas@gmail.com\",\"address\":\"VILNIUS\",\"password\":\"asd32a_sqwesAdasd\"}]";

        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .get()
                .uri(requestMapping)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().json(expected);
    }

    @Test
    @Order(2)
    void testGetUserById() {
        String expected = "{\"id\":1,\"name\":\"Kestas\",\"surname\":\"Kestauskas\",\"phoneNumber\":\"+37063123823\",\"email\":\"kkestas@gmail.com\",\"address\":\"VILNIUS\",\"password\":\"qwasDD_qw123\"}";

        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .get()
                .uri(requestMapping + 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().json(expected);
    }

    @Test
    @Order(3)
    void testAddUser() {
        User user = new User("Kestas", "Kestauskas", "+37063123823", "kkestas@gmail.com", "VILNIUS", "qwasDD!_qw123");

        String expected = "{\"id\":5,\"name\":\"Kestas\",\"surname\":\"Kestauskas\",\"phoneNumber\":\"+37063123823\",\"email\":\"kkestas@gmail.com\",\"address\":\"VILNIUS\",\"password\":\"qwasDD!_qw123\"}";

        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .post()
                .uri(requestMapping)
                .bodyValue(user)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().json(expected);

        userService.deleteById(5L);
    }

    @Test
    @Order(4)
    void testUpdateUser() {
        User user = new User(4L, "TEST", "TESTAS", "+37063123823", "kkestas@gmail.com", "VILNIUS", "qwasDD!_qw123");

        String expected = "{\"id\":4,\"name\":\"TEST\",\"surname\":\"TESTAS\",\"phoneNumber\":\"+37063123823\",\"email\":\"kkestas@gmail.com\",\"address\":\"VILNIUS\",\"password\":\"qwasDD!_qw123\"}";

        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .put()
                .uri(requestMapping).bodyValue(user)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expected);
    }

    @Test
    @Order(5)
    void testDeleteUserById() {
        User newUser = new User("Username", "Surname", "+37063123823", "user@gmail.com", "User location", "qwasDD!_qw123");

        userService.add(newUser);

        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .delete()
                .uri(requestMapping + newUser.getId())
                .exchange()
                .expectStatus().isOk();
    }
}
