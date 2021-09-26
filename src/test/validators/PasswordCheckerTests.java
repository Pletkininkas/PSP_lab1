package validators;

import com.vu.psp.evaldasg.validators.PasswordChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordCheckerTests {

    PasswordChecker passwordChecker;

    @BeforeEach
    void setUp()
    {
        passwordChecker = new PasswordChecker();
    }

    @Test
    void TestValidatePassword_PasswordIsValid_ExpectedResultTrue()
    {
        assertTrue(passwordChecker.validatePassword(10, "Qwertyuio?"));
    }

    @Test
    void TestValidatePassword_PasswordIsTooShort_ExpectedResultFalse()
    {
        assertFalse(passwordChecker.validatePassword(10, "Qwertyu?"));
    }

    @Test
    void TestValidatePassword_PasswordDoesNotContainUppercaseSymbol_ExpectedResultFalse()
    {
        assertFalse(passwordChecker.validatePassword(10, "qwertyuio?"));
    }

    @Test
    void TestValidatePassword_PasswordDoesNotContainSpecialSymbol_ExpectedResultFalse()
    {
        assertFalse(passwordChecker.validatePassword(10, "Qwertyuiout"));
    }

}