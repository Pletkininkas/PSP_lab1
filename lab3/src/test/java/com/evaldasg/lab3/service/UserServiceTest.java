package com.evaldasg.lab3.service;

import com.evaldasg.lab3.entity.User;
import com.evaldasg.lab3.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void testFindAllUsers() {
        User user1 = new User(1L, "Test1", "Surname", "+37061234561", "test@test.com", "Location place", "Pass123_");
        User user2 = new User(2L, "Test2", "Surname", "+37061234561", "test@test.com", "Location place", "Pass123_");
        List<User> users = new ArrayList<>();

        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        assertEquals(2, userService.findAll().size());
    }

    @Test
    void testFindUserById() {
        Long id = 4L;
        User user = new User(id, "Test1", "Surname", "+37061234561", "test@test.com", "Location place", "Pass123_");

        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(user));

        assertEquals(user, userService.findById(id));
    }

    @Test
    void testAddUser() {
        User user = new User("Test1", "Surname", "+37061234561", "test@test.com", "Location place", "Pass123_");
        User userRepositoryResponse = new User(1L, "Test1", "Surname", "+37061234561", "test@test.com", "Location place", "Pass123_");

        when(userRepository.save(Mockito.any(User.class))).thenReturn(userRepositoryResponse);

        User createdUser = userService.add(user);

        assertEquals(userRepositoryResponse, createdUser);
        verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    void testUpdateUser() {
        User user = new User(1L, "Test1", "Surname", "+37061234561", "test@test.com", "Location place", "Pass123_");
        User updatedUser = new User(1L, "Test3", "Adasddq", "+37069934333", "notest@test.com", "Locationplace", "Paerw123_");

        when(userRepository.save(user)).thenReturn(updatedUser);

        assertEquals(updatedUser, userService.update(user));
        verify(userRepository).save(user);
    }

    @Test
    void testDeleteUserById() {
        userService.deleteById(1L);

        verify(userRepository).deleteById(Mockito.anyLong());
    }
}
