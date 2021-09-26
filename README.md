PSP_Laboratory Work 1
-----
Implemented Unit tests for Phone, Email and Password validators.

PSP_Laboratory Work 2
-----
Target tests from Aivaras Rinkevičius.

Feedback:

- Tests were not enough to ensure correct TDD process for a programmer who is implementing this feature.
- For example:
- - Email is lacking comments section in local-part which could be tes(<>?:)t123@gmail.com and is resolved to => test123@gmail.com.
- - Email unit test is not checking if email local-part or domain labels does not exceed maximum length of 63 per label. Resource: https://en.wikipedia.org/wiki/Email_address#:~:text=each%20label%20being%20limited%20to%20a%20length%20of%2063%20characters%20and%20consisting%20of
- - In email tests you could have used different domains instead of one correct constant 'google.com'. It actually makes tests pass with constant which will probably be not expected result in production code.

- PhoneValidator.
- - Interpretation left for constructors. Should I define LT prefix and length in Phone validator class or should I pass it in constructor, or should the test 'TestValidatePhone_PhoneIsValid_ExpectedResultTrue' half times fail and half times succeed?

- PasswordChecker.
- - Why should anyone declare password length each time when trying to validate? Maybe it is better to have a static PasswordChecker class in order to pass to validate method only password input since each test has the same constant of 10.
- - Also, do you check when password is null? I think it is not desired value to receive NullPointerException in production code.

I would add null checks for String variables.
More tests for email in different cases according to previously mention resource.
Would change the validation implementation in PhoneValidator since passed variable minLength is redundant.
Check correct lengths for input values according to validation rules.