package com.evaldasg.lab3.controller;

import com.evaldasg.lab3.entity.User;
import com.evaldasg.lab3.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(value = UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User(1L, "Kestas", "Kestauskas", "+37063123823", "kkestas@gmail.com", "VILNIUS", "qwasdd!qw123"),
                new User(2L, "Testas", "Tests", "+37063123823", "ttestas@gmail.com", "VILNIUS", "ads1swqwed1!")
        );

        String expectedUsersJson = objectMapper.writeValueAsString(users);

        when(userService.findAll()).thenReturn(users);

        RequestBuilder rb = MockMvcRequestBuilders
                .get("/")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        JSONAssert.assertEquals(expectedUsersJson, result.getResponse().getContentAsString(), false);
    }

    @Test
    void getUserById() throws Exception {
        Long id = 1L;
        User user = new User(id, "Kestas", "Kestauskas", "+37063123823", "kkestas@gmail.com", "VILNIUS", "qwasdd!qw123");

        when(userService.findById(Mockito.anyLong())).thenReturn(user);

        RequestBuilder rb = MockMvcRequestBuilders
                .get("/" + id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String expectedUser = objectMapper.writeValueAsString(user);

        JSONAssert.assertEquals(expectedUser, result.getResponse().getContentAsString(), false);
    }

    @Test
    void addUser() throws Exception {
        User user = new User(1L, "Kestas", "Kestauskas", "+37063123823", "kkestas@gmail.com", "VILNIUS", "qwasdd!qw123");

        when(userService.add(Mockito.any(User.class))).thenReturn(user);

        String userJson = objectMapper.writeValueAsString(user);

        RequestBuilder rb = MockMvcRequestBuilders
                .post("/")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        JSONAssert.assertEquals(userJson, result.getResponse().getContentAsString(), false);
    }

    @Test
    void updateUser() throws Exception {
        User user = new User(1L, "Kestas", "Kestauskas", "+37063123823", "kkestas@gmail.com", "VILNIUS", "qwaASD_qw123");

        String userJson = objectMapper.writeValueAsString(user);

        RequestBuilder rb = MockMvcRequestBuilders
                .patch("/")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
    }

    @Test
    void deleteUserById() throws Exception {
        when(userService.findById(Mockito.anyLong())).thenReturn(new User());

        RequestBuilder rb = MockMvcRequestBuilders
                .delete("/" + 1L);

        mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(userService).deleteById(Mockito.anyLong());
    }
}
