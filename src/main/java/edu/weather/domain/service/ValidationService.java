package edu.weather.domain.service;

import edu.weather.controller.dto.UserDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationService {

    public boolean isValidLogin(String login){
        // Перевірка, що логін не є порожнім або null
        return login != null && !login.isEmpty();
    }

    public boolean isValidPassword(String password){
        // Перевірка, що пароль не є порожнім або null
        if (password == null || password.isEmpty()) {
            return false;
        }

        // Перевірка, що пароль містить від 3 до 25 символів
        if (password.length() < 3 || password.length() > 25) {
            return false;
        }

        // Перевірка, що пароль містить принаймні одну велику літеру
        Pattern uppercasePattern = Pattern.compile(".*[A-Z].*");
        Matcher uppercaseMatcher = uppercasePattern.matcher(password);
        if (!uppercaseMatcher.matches()) {
            return false;
        }

        // Перевірка, що пароль містить принаймні одну цифру
        Pattern digitPattern = Pattern.compile(".*\\d.*");
        Matcher digitMatcher = digitPattern.matcher(password);
        if (!digitMatcher.matches()) {
            return false;
        }

        // Перевірка, що пароль містить принаймні один спецсимвол
        Pattern specialCharPattern = Pattern.compile(".*[@!#$%^&*()\\[\\]{};:,.<>?].*");
        Matcher specialCharMatcher = specialCharPattern.matcher(password);
        if (!specialCharMatcher.matches()) {
            return false;
        }

        return true; // Пароль відповідає всім вимогам
    }
}
