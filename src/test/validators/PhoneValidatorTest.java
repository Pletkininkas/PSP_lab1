package validators;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
    AAA pattern (Arrange, Act and Assert)
    Link: https://java-design-patterns.com/patterns/arrange-act-assert/
 */

public class PhoneValidatorTest {

    static PhoneValidator phoneValidator;

    @BeforeAll
    public static void setupEnv() {
        phoneValidator = new PhoneValidator();
    }

    @Test
    public void Should_ValidatePhoneNumber_When_PhoneNumberIsCorrect() {
        String phoneNumber = "868123456";

        assertEquals("+37068123456", phoneValidator.validate(phoneNumber));
    }

    @Test
    public void Should_ValidatePhoneNumber_When_PhoneNumberIsCorrectWithCompleteForm() {
        String phoneNumber = "+37068123678";

        assertEquals("+37068123678", phoneValidator.validate(phoneNumber));
    }

    @Test
    public void Should_ThrowException_When_PhoneNumberIsNull() {
        String phoneNumber = null;

        assertThrows(InvalidPhoneNumberException.class, () -> {
            phoneValidator.validate(phoneNumber);
        });
    }

    @Test
    public void Should_ThrowException_When_PhoneNumberHasNoRules() {
        String phoneNumber = "0768123678";

        assertThrows(InvalidPhoneNumberException.class, () -> {
            phoneValidator.validate(phoneNumber);
        });
    }

    @Test
    public void Should_ThrowException_When_PhoneNumberConsistsOfNotOnlyDigits() {
        String phoneNumber = "86*123678";

        assertThrows(InvalidPhoneNumberException.class, () -> {
            phoneValidator.validate(phoneNumber);
        });
    }

    @Test
    public void Should_ThrowException_When_PhoneNumberWithCompleteFormIsTooSmall() {
        String phoneNumber = "+3706812345";

        assertThrows(InvalidPhoneNumberException.class, () -> {
            phoneValidator.validate(phoneNumber);
        });
    }

    @Test
    public void Should_ThrowException_When_PhoneNumberIsTooSmall() {
        String phoneNumber = "86812345";

        assertThrows(InvalidPhoneNumberException.class, () -> {
            phoneValidator.validate(phoneNumber);
        });
    }
}
