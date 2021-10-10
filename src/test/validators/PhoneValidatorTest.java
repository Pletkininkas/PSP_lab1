package validators;

import com.vu.psp.evaldasg.validators.PhoneValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhoneValidatorTest {

    PhoneValidator phoneValidator;

    @BeforeEach
    void setUp()
    {
        phoneValidator = new PhoneValidator();
        phoneValidator.setPrefix("LT","+370", 12);
        phoneValidator.setPrefix("PL","+48", 12);
        phoneValidator.setPrefixToChange("LT","8");
    }

    @ParameterizedTest
    @CsvSource({"868686866,LT", "+37068686866,LT", "+48123456789,PL"})
    void TestValidatePhone_PhoneIsValid_ExpectedResultTrue(String number, String country)
    {
        assertTrue(phoneValidator.validate(number, country));
    }

    @Test
    void TestValidatePhone_PhoneNumberContainsOtherSymbols_ExpectedResultFalse()
    {
        assertFalse(phoneValidator.validate("+37068gs+866","LT"));
    }

    @Test
    void TestValidatePhone_PhoneNumberIsTooShort_ExpectedResultFalse()
    {
        assertFalse(phoneValidator.validate("+48686", "PL"));
    }

    @Test
    void TestValidatePhone_PhoneNumberPrefixIsWrongBasedOnCountry_ExpectedResultFalse()
    {
        assertFalse(phoneValidator.validate("+37068686866", "PL"));
    }

    @Test
    void TestValidatePhone_PhoneNumberIsNull_ExpectedResultFalse()
    {
        assertFalse(phoneValidator.validate(null, "PL")); // Test fix. Added second parameter
    }
}
