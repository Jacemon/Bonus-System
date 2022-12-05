package by.jcompany.bonus_system.util;

import java.util.regex.Pattern;

public class Validator {
    private static final Pattern loginRegex = Pattern.compile("^[a-z][a-z0-9]*?([-_][a-z0-9]+){0,2}$");
    
    public static boolean correctLogin(String login) {
        return loginRegex.matcher(login).matches();
    }
}
