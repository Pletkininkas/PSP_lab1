package com.evaldasg.lab3.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    void testDoctorCreationWithAllFieldsSet() {
        User user = new User(1L, "Name", "Surname", "+3706123456", "test@test.com", "Location place", "Pass123_");
        assertAll("Test User constructor",
                () -> assertEquals(1L, user.getId()),
                () -> assertEquals("Name", user.getName()),
                () -> assertEquals("Surname", user.getSurname()),
                () -> assertEquals("+3706123456", user.getPhoneNumber()),
                () -> assertEquals("test@test.com", user.getEmail()),
                () -> assertEquals("Location place", user.getAddress()),
                () -> assertEquals("Pass123_", user.getPassword())
        );
    }

    @Test
    void testDoctorNewCreationToDaoConstructor() {
        User user = new User("Name", "Surname", "+3706123456", "test@test.com", "Location place", "Pass123_");
        assertAll("Test User constructor",
                () -> assertNull(user.getId()),
                () -> assertEquals("Name", user.getName()),
                () -> assertEquals("Surname", user.getSurname()),
                () -> assertEquals("+3706123456", user.getPhoneNumber()),
                () -> assertEquals("test@test.com", user.getEmail()),
                () -> assertEquals("Location place", user.getAddress()),
                () -> assertEquals("Pass123_", user.getPassword())
        );
    }

    @Test
    void testEquals() {
        User user1 = new User("Name", "Surname", "+3706123456", "test@test.com", "Location place", "Pass123_");
        User user2 = new User("Name", "Surname", "+3706123456", "test@test.com", "Location place", "Pass123_");
        assertEquals(user1, user2);
    }

    @Test
    void testSetId() {
        Long newId = 123L;
        User user = new User();

        user.setId(newId);

        assertEquals(newId, user.getId());
    }

    @Test
    void testSetName() {
        String newName = "test";
        User user = new User();

        user.setName(newName);

        assertEquals(newName, user.getName());
    }

    @Test
    void testSetSurname() {
        String newName = "test";
        User user = new User();

        user.setSurname(newName);

        assertEquals(newName, user.getSurname());
    }

    @Test
    void testSetPhoneNumber() {
        String newPhoneNumber = "+3706123456";
        User user = new User();

        user.setPhoneNumber(newPhoneNumber);

        assertEquals(newPhoneNumber, user.getPhoneNumber());
    }

    @Test
    void testSetEmail() {
        String newEmail = "test@test.com";
        User user = new User();

        user.setEmail(newEmail);

        assertEquals(newEmail, user.getEmail());
    }

    @Test
    void testSetAddress() {
        String newAddress = "Location place";
        User user = new User();

        user.setAddress(newAddress);

        assertEquals(newAddress, user.getAddress());
    }

    @Test
    void testSetPassword() {
        String newPassword = "Pass123_";
        User user = new User();

        user.setPassword(newPassword);

        assertEquals(newPassword, user.getPassword());
    }
}
