package com.vu.psp.evaldasg.validators;

import java.util.HashMap;
import java.util.Map;

public class PhoneValidator {

    private final Map<String, String> prefixes = new HashMap<>();
    private final Map<String, Integer> lengths = new HashMap<>();

    public PhoneValidator() {
        prefixes.put("LT", "+370");
        lengths.put("LT", 12);
    }

    public boolean validatePhone(String number, String country) {
        String prefix = prefixes.get(country);
        int length = lengths.get(country);

        if (number.startsWith(prefix) && number.length() == length) {
            return validate(number, length);
        } else if ((number.length() - 1 + prefix.length()) == length) {
            String numberWithPrefix = prefix + number.substring(1);
            return validate(numberWithPrefix, length);
        } else {
            return false;
        }
    }

    public void setPrefix(String country, String prefix) {
        if (country != null && prefix != null) {
            prefixes.put(country, prefix);
        }
    }

    public void setLength(String country, int length) {
        if (country != null) {
            lengths.put(country, length);
        }
    }

    private boolean validate(String number, int length) {
        for (int i = 0; i < length; i++) {
            char currentChar = number.charAt(i);
            if ((i == 0 && currentChar == '+') || isDigit(currentChar))
                continue;
            return false;
        }
        return true;
    }

    private boolean isDigit(char c) {
        return (c >= 48 && c <= 57);
    }
}
