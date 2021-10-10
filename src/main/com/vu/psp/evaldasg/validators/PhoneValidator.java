package com.vu.psp.evaldasg.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneValidator {

    private final Map<String, String> prefixes = new HashMap<>();
    private final Map<String, Integer> lengths = new HashMap<>();
    private Map<String, List<String>> prefixesChange = new HashMap<>();

    public PhoneValidator() {
    }

    public boolean validate(String number, String country) {
        if (number == null || country == null)
            return false;

        if (!prefixes.containsKey(country) || !lengths.containsKey(country))
            return false;

        String prefix = prefixes.get(country);
        List<String> prefixesToChange = prefixesChange.get(country);
        int length = lengths.get(country);

        if (number.startsWith(prefix) && number.length() == length) {
            return validate(number, length);
        } else {
            boolean containsPrefixToChange = false;
            String foundChangePrefix = null;
            if (prefixesToChange != null && prefixesToChange.size() != 0) {
                for (String changePrefix : prefixesToChange) {
                    if (number.startsWith(changePrefix)) {
                        foundChangePrefix = changePrefix;
                        containsPrefixToChange = true;
                    }
                }
            }
            if (!containsPrefixToChange) {
                return false;
            } else {
                validateWithPrefixChange(number, length, foundChangePrefix, country);
            }
        }
        if ((number.length() - 1 + prefix.length()) == length) {
            String numberWithPrefix = prefix + number.substring(1);
            return validate(numberWithPrefix, length);
        } else {
            return false;
        }
    }

    public void setPrefix(String country, String prefix, int length) {
        if (country != null && prefix != null) {
            prefixes.put(country, prefix);
            lengths.put(country, length);
        }
    }

    public void setPrefixToChange(String country, String changePrefix) {
        if (!this.prefixesChange.containsKey(country)) {
            List<String> newList = new ArrayList<>();
            newList.add(changePrefix);
            this.prefixesChange.put(country, newList);
        } else {
            List<String> currentList = this.prefixesChange.get(country);
            if (!currentList.contains(changePrefix)) {
                currentList.add(changePrefix);
                this.prefixesChange.replace(country, currentList);
            }
        }
    }

    public void removePrefixToChange(String country, String changePrefix) {
        if (this.prefixesChange.containsKey(country)) {
            List<String> list = this.prefixesChange.get(country);
            if (list.contains(changePrefix)) {
                list.remove(changePrefix);
                this.prefixesChange.replace(country, list);
            }
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

    private boolean validateWithPrefixChange(String number, int length, String prefixToChange, String country) {
        String fullPrefix = prefixes.get(country);
        String fullChangedPassword = fullPrefix + number.substring(prefixToChange.length());
        return validate(fullChangedPassword, length);
    }

    private boolean isDigit(char c) {
        return (c >= 48 && c <= 57);
    }
}
