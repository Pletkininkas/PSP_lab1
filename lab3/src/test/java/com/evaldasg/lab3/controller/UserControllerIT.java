package com.evaldasg.lab3.controller;

import com.evaldasg.lab3.Lab3Application;
import com.evaldasg.lab3.entity.User;
import com.evaldasg.lab3.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
    void testRetrieveAllUsers() {
        String expected = "[{\"id\":1,\"name\":\"Kestas\",\"surname\":\"Kestauskas\",\"phoneNumber\":\"+37063123823\",\"email\":\"kkestas@gmail.com\",\"address\":\"VILNIUS\",\"password\":\"qwasDD_qw123\"},{\"id\":2,\"name\":\"Testas\",\"surname\":\"Tests\",\"phoneNumber\":\"+37063123823\",\"email\":\"ttestas@gmail.com\",\"address\":\"VILNIUS\",\"password\":\"ads1sw_Qwed1\"},{\"id\":3,\"name\":\"Petras\",\"surname\":\"Petrauskas\",\"phoneNumber\":\"+37063123823\",\"email\":\"ppetrauskas@gmail.com\",\"address\":\"VILNIUS\",\"password\":\"asd_d213aSqweasd\"},{\"id\":4,\"name\":\"Jonas\",\"surname\":\"Jonauskas\",\"phoneNumber\":\"+37063123823\",\"email\":\"jjonauskas@gmail.com\",\"address\":\"VILNIUS\",\"password\":\"asd32a_sqwesAdasd\"}]";

        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .get()
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().json(expected);
    }

    @Test
    void testGetUserById() {
        String expected = "{\"id\":1,\"name\":\"Kestas\",\"surname\":\"Kestauskas\",\"phoneNumber\":\"+37063123823\",\"email\":\"kkestas@gmail.com\",\"address\":\"VILNIUS\",\"password\":\"qwasDD_qw123\"}";

        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .get()
                .uri("/" + 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().json(expected);
    }

    @Test
    void testAddUser() {
        User user = new User("Kestas", "Kestauskas", "+37063123823", "kkestas@gmail.com", "VILNIUS", "qwasDD!_qw123");
        String expected = "{\"id\":5,\"name\":\"Kestas\",\"surname\":\"Kestauskas\",\"phoneNumber\":\"+37063123823\",\"email\":\"kkestas@gmail.com\",\"address\":\"VILNIUS\",\"password\":\"qwasDD!_qw123\"}";

        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .post()
                .bodyValue(user)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().json(expected);

        userService.deleteById(5L);
    }

    @Test
    void testUpdateUser() {
        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .put()
                .uri("/").bodyValue(user)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeleteUserById() {
        User newUser = new User("Username", "Surname", "+37063123823", "user@gmail.com", "User location", "qwasDD!_qw123");
        userService.add(newUser);
        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .delete()
                .uri("/" + newUser.getId())
                .exchange()
                .expectStatus().isOk();
    }
}
