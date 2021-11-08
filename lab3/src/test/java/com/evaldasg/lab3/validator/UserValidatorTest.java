package com.evaldasg.lab3.validator;

import com.evaldasg.lab3.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserValidatorTest {

    UserValidator userValidator = new UserValidator();

    @Test
    public void Should_ValidateUser_When_UserFormIsCorrect() {
        User user = new User(1L, "Name", "Surname", "+37068123456", "test@test.com", "Address location", "Pass_1234");
        assertDoesNotThrow(() -> userValidator.validateUser(user));
    }

    @Test
    public void Should_ThrowException_When_UserFormPhoneNumberIsInvalid() {
        User user = new User(1L, "Name", "Surname", "+370681234", "test@test.com", "Address location", "Pass_1234");
        assertThrows(ResponseStatusException.class, () -> userValidator.validateUser(user));
    }

    @Test
    public void Should_ThrowException_When_UserFormEmailIsInvalid() {
        User user = new User(1L, "Name", "Surname", "+37068123456", "teƒst@test.com", "Address location", "Pass_1234");
        assertThrows(ResponseStatusException.class, () -> userValidator.validateUser(user));
    }

    @Test
    public void Should_ThrowException_When_UserFormPasswordIsInvalid() {
        User user = new User(1L, "Name", "Surname", "+37068123456", "test@test.com", "Address location", "pass_1234");
        assertThrows(ResponseStatusException.class, () -> userValidator.validateUser(user));
    }
}
