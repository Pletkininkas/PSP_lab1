package validators;

import com.vu.psp.evaldasg.validators.EmailValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EmailValidatorTest {

    EmailValidator emailValidator;

    @BeforeEach
    void setUp()
    {
        emailValidator = new EmailValidator();
        emailValidator.setСorrectDomain("gmail.com");
        emailValidator.setСorrectDomain("yahoo.com");
    }

    @ParameterizedTest
    @ValueSource(strings = {"qwerty@gmail.com", "qwerty@yahoo.com"})
    void TestValidateEmail_EmailIsValid_ExpectedResultTrue(String email)
    {
        assertTrue(emailValidator.validate(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"qwerty(hello)@gmail.com", "qwer(John?)ty@yahoo.com"})
    void TestValidateEmail_EmailContainsComments_ExpectedResultTrue(String email)
    {
        assertTrue(emailValidator.validate(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"qwertygmail.com", "qwertyyahoo.com"})
    void TestValidateEmail_EmailDoesNotContainEtaSymbol_ExpectedResultFalse(String email)
    {
        assertFalse(emailValidator.validate(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"qw©rty@gmail.com", "qwr±ty@yahoo.com"}) // Changed second ValueSource since & is valid symbol in local part: qwr&ty@yahoo.com => qwr±ty@yahoo.com
    void TestValidateEmail_EmailContainsIllegalSymbols_ExpectedResultFalse(String email)
    {
        assertFalse(emailValidator.validate(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234567890123456789012345678901234567890123456789012345678901234+x@gmail.com", "1234567890123456789012345678901234567890123456789012345678901234+x@yahoo.com"})
    void TestValidateEmail_LocalPartOfEmailIsLongerThanMaximum64Characters_ExpectedResultFalse(String email)
    {
        assertFalse(emailValidator.validate(email));
    }


    @ParameterizedTest
    @ValueSource(strings = {"qwerty@ail.com", "qwerty@yaho.com"})
    void TestValidateEmail_EmailContainsWrongDomain_ExpectedResultFalse(String email)
    {
        assertFalse(emailValidator.validate(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"qwerty@gail.c", "qwerty@yahoo.cm"})
    void TestValidateEmail_EmailContainsWrongTopLevelDomain_ExpectedResultFalse(String email)
    {
        assertFalse(emailValidator.validate(email));
    }

    @Test
    void TestValidateEmail_EmailIsNull_ExpectedResultFalse(){
        assertFalse(emailValidator.validate(null));
    }

}
