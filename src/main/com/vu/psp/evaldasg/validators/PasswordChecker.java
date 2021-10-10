package com.vu.psp.evaldasg.validators;

public class PasswordChecker {

    private char[] specialCharacters;
    private final int length;

    public PasswordChecker(int length) {
        this.length = length;
    }

    public boolean validate(String password) {
        boolean upperCase = false;
        boolean specialSymbol = false;

        if (password == null)
            return false;

        if (length > password.length())
            return false;

        for(int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);
            if (currentChar >= 65 && currentChar <= 90)
                upperCase = true;
            for(int j = 0; j < specialCharacters.length; j++) {
                if (currentChar == specialCharacters[j]) {
                    specialSymbol = true;
                    break;
                }
            }
            if (upperCase && specialSymbol)
                break;
        }

        return upperCase && specialSymbol;
    }

    public void setCustomCharacters(String characters) {
        this.specialCharacters = characters.toCharArray();
    }
}
