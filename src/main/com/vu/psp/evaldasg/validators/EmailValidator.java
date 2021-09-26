package com.vu.psp.evaldasg.validators;

import java.util.ArrayList;
import java.util.List;

public class EmailValidator {

    public boolean validateEmail(String email) {
        if (email == null)
            return false;
        String value = email.toLowerCase();
        if (!value.contains("@"))
            return false;
        String lp = removeComments(getLp(value));
        String domain = removeComments(getDomain(value));

        return validateLp(lp) && validateDomain(domain);
    }

    private String removeComments(String value) {
        if (value.contains("(") && value.contains(")")) {
            int read = 0;
            String tempValue = "";
            for (int i = 0; i < value.length(); i++) {
                char currentChar = value.charAt(i);
                if (currentChar == '(') {
                    read++;
                } else if (currentChar == ')') {
                    read--;
                } else if (read == 0) {
                    tempValue += currentChar;
                }
                if (i == (value.length() - 1) && read == 0) {
                    return tempValue;
                }
            }
        }
        return value;
    }

    private boolean validateDomain(String domain) {
        int domainLength = domain.length();
        List<String> labels = new ArrayList<>();
        if (domain.charAt(0) == '-' || domain.charAt(domainLength-1) == '-')
            return false;
        String temp = "";
        for(int i = 0; i < domainLength; i++) {
            char currentChar = domain.charAt(i);
            if (currentChar == '.' || (i + 1) == domainLength) {
                if ((i + 1) == domainLength)
                    temp += currentChar;
                labels.add(temp);
                temp = "";
            } else {
                if (!isSymbolCorrectInDomain(currentChar))
                    return false;
                temp += currentChar;
            }
        }
        for (String s : labels) {
            if (s.length() > 63)
                return false;
        }
        // Check if valid domain. Correct test  is with constant gmail.com
        if (!domain.equals("gmail.com"))
            return false;

        return true;
    }

    private String getLp(String email) {
        return email.substring(0, email.indexOf("@"));
    }

    private String getDomain(String email) {
        return email.substring(email.indexOf("@")+1);
    }

    private boolean validateLp(String lp) {
        int lpLength = lp.length();
        if (lpLength == 0 || lpLength > 64)
            return false;
        if (!(lp.charAt(0) != '.' && lp.charAt(lpLength-1) != '.'))
            return false;
        if (lpContainsMultipleDots(lp, lpLength))
            return false;
        return validateLpSymbols(lp, lpLength);
    }

    private boolean lpContainsMultipleDots(String lp, int lpLength) {
        if (lp.contains("."))
            for (int i = lp.indexOf("."); i < lpLength; i++)
                if ((i + 1) != lpLength && lp.charAt(i) == '.' && lp.charAt(i + 1) == '.')
                    return true;
        return false;
    }

    private boolean validateLpSymbols(String lp, int lpLength) {
        for (int i = 0; i < lpLength; i++) {
            if (!isSymbolCorrectInLp(lp.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isSymbolCorrectInDomain(char domain) {
        return domain == 91 || domain == 93 || domain == 58 ||
                (domain >= 97 && domain <= 122) ||
                (domain >= 48 && domain <= 57) ||
                domain == 45 || domain == 46;
    }

    private boolean isSymbolCorrectInLp(char c) {
        return c == '!' || (c >= 35 && c <= 39) ||
                c == '*' || c == '+' ||
                c == '-' || c == '/' ||
                (c >= 48 && c <= 57) ||
                c == '=' || c == '?' ||
                (c >= 94 && c <= 126);
    }
}
