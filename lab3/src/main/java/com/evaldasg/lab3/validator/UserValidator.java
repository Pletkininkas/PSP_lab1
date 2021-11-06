package com.evaldasg.lab3.validator;

import businessRequirements.Validations.EmailValidator;
import businessRequirements.Validations.PasswordChecker;
import businessRequirements.Validations.PhoneValidator;
import com.evaldasg.lab3.entity.User;
import com.evaldasg.lab3.validator.constant.UserField;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

public class UserValidator {

    private final PasswordChecker passwordChecker = new PasswordChecker();
    private final EmailValidator emailValidator = new EmailValidator();
    private final PhoneValidator phoneValidator = new PhoneValidator();

    public void validateUser(User user, List<UserField> userFields) {
        if (userFields.contains(UserField.PASSWORD)) validatePassword(user.getPassword());
        if (userFields.contains(UserField.EMAIL)) validateEmail(user.getEmail());
        if (userFields.contains(UserField.PHONE_NUMBER)) validatePhoneNumber(user.getPhoneNumber());
    }

    public void validateUser(User user) {
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
        validatePhoneNumber(user.getPhoneNumber());
    }

    private void validatePassword(String password) {
        int minPasswordLength = 8;
        try {
            throwBadRequestStatusIfFalse(passwordChecker.hasSpecialCharacter(password), "Password should have special character.");
            throwBadRequestStatusIfFalse(passwordChecker.hasUppercaseLetter(password), "Password should have upperCase letters.");
            throwBadRequestStatusIfFalse(passwordChecker.hasLengthRequirement(password, minPasswordLength), "Password should contain at least " + minPasswordLength + " characters.");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong processing password. Contact administration.");
        }
    }

    private void validateEmail(String email) {
        try {
            throwBadRequestStatusIfFalse(emailValidator.domainValidator(email), "Email domain is invalid.");
            throwBadRequestStatusIfFalse(emailValidator.hasAtSymbol(email), "Email does not have @ symbol.");
            throwBadRequestStatusIfFalse(emailValidator.topLevelDomainValidator(email), "Email top level domain is invalid.");
            throwBadRequestStatusIfFalse(emailValidator.hasInvalidSymbol(email), "Email has invalid symbol.");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong processing password. Contact administration.");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        try {
            throwBadRequestStatusIfFalse(phoneValidator.hasLetters(phoneNumber), "Phone number has letters.");
            phoneNumber = phoneValidator.numberFormat(phoneNumber);

            boolean valid = phoneValidator.differentCountryNumberValidation("LT", phoneNumber) |
                    phoneValidator.differentCountryNumberValidation("FR", phoneNumber) |
                    phoneValidator.differentCountryNumberValidation("ENG", phoneNumber);

            throwBadRequestStatusIfFalse(valid, "Phone number from this country is not supported.");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong processing password. Contact administration.");
        }
    }

    private void throwBadRequestStatusIfFalse(boolean value, String errorMessage) {
        if (!value)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
    }
}
