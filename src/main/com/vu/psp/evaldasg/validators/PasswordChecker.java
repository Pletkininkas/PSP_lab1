package com.vu.psp.evaldasg.validators;

public class PasswordChecker {

    final char[] specialSymbols;

    public PasswordChecker() {
        specialSymbols = "_)(+=?".toCharArray();
    }

    public PasswordChecker(String specialSymbols) {
        this.specialSymbols = specialSymbols.toCharArray();
    }

    public PasswordChecker(char[] specialSymbols) {
        this.specialSymbols = specialSymbols;
    }

    public boolean validatePassword(int length, String password) {
        boolean upperCase = false;
        boolean specialSymbol = false;

        if (length > password.length())
            return false;

        for(int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);
            if (currentChar >= 65 && currentChar <= 90)
                upperCase = true;
            for(int j = 0; j < specialSymbols.length; j++) {
                if (currentChar == specialSymbols[j]) {
                    specialSymbol = true;
                    break;
                }
            }
            if (upperCase && specialSymbol)
                break;
        }

        return upperCase && specialSymbol;
    }
}
