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

    private final String requestMapping = "/user/";

    @Test
    void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User(1L, "Kestas", "Kestauskas", "+37063123823", "kkestas@gmail.com", "VILNIUS", "qwasdd!qw123"),
                new User(2L, "Testas", "Tests", "+37063123823", "ttestas@gmail.com", "VILNIUS", "ads1swqwed1!")
        );

        String expectedUsersJson = objectMapper.writeValueAsString(users);

        when(userService.findAll()).thenReturn(users);

        RequestBuilder rb = MockMvcRequestBuilders
                .get(requestMapping)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        JSONAssert.assertEquals(expectedUsersJson, result.getResponse().getContentAsString(), false);
    }

    @Test
    void testGetUserById() throws Exception {
        Long id = 1L;
        User user = new User(id, "Kestas", "Kestauskas", "+37063123823", "kkestas@gmail.com", "VILNIUS", "qwasdd!qw123");
        String expectedUser = objectMapper.writeValueAsString(user);

        when(userService.findById(Mockito.anyLong())).thenReturn(user);

        RequestBuilder rb = MockMvcRequestBuilders
                .get(requestMapping + id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        JSONAssert.assertEquals(expectedUser, result.getResponse().getContentAsString(), false);
    }

    @Test
    void testAddUser() throws Exception {
        User user = new User(1L, "Kestas", "Kestauskas", "+37063123823", "kkestas@gmail.com", "VILNIUS", "qwasdd!qw123");
        String userJson = objectMapper.writeValueAsString(user);

        when(userService.add(Mockito.any(User.class))).thenReturn(user);

        RequestBuilder rb = MockMvcRequestBuilders
                .post(requestMapping)
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
    void testUpdateUser() throws Exception {
        User user = new User(1L, "TEST", "NAME", "+37063123823", "kkestas@gmail.com", "VILNIUS", "qwaASD_qw123");
        String userJson = objectMapper.writeValueAsString(user);

        when(userService.update(Mockito.any(User.class))).thenReturn(user);
        when(userService.findById(Mockito.anyLong())).thenReturn(user);

        RequestBuilder rb = MockMvcRequestBuilders
                .put(requestMapping)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        JSONAssert.assertEquals(userJson, result.getResponse().getContentAsString(), false);
    }

    @Test
    void testDeleteUserById() throws Exception {
        User user = new User(1L, "Test1", "Surname", "+37061234561", "test@test.com", "Location place", "Pass123_");

        when(userService.findById(Mockito.anyLong())).thenReturn(user);

        RequestBuilder rb = MockMvcRequestBuilders
                .delete(requestMapping + user.getId());

        mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(userService).deleteById(Mockito.anyLong());
    }
}
