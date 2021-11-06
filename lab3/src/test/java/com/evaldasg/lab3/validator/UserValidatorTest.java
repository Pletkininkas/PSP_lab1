package com.evaldasg.lab3.validator;

import com.evaldasg.lab3.entity.User;
import com.evaldasg.lab3.validator.constant.UserField;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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
    public void ValidatorWithAllDefinedValidatingFields_Should_ValidateUser_When_UserFormIsValid() {
        User user = new User(1L, "Name", "Surname", "+37068123456", "test@test.com", "Address location", "Pass_1234");
        List<UserField> userFields = new ArrayList<>();
        userFields.add(UserField.EMAIL);
        userFields.add(UserField.PASSWORD);
        userFields.add(UserField.PHONE_NUMBER);

        assertDoesNotThrow(() -> userValidator.validateUser(user));
    }

    @Test
    public void ValidatorWithDefinedFields_PasswordAndEmail_Should_ValidateUser_Without_ValidatingPhoneNumber() {
        User user = new User(1L, "Name", "Surname", "+6657", "test@test.com", "Address location", "Pass_1234");
        List<UserField> userFields = new ArrayList<>();
        userFields.add(UserField.EMAIL);
        userFields.add(UserField.PASSWORD);

        assertDoesNotThrow(() -> userValidator.validateUser(user, userFields));
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
