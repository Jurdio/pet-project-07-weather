package edu.weather.domain.service;

import jakarta.servlet.http.Cookie;

import java.util.Arrays;
import java.util.Optional;

public class CookieService {
    public Optional<Cookie> findCookieByName(Cookie[] cookies, String cookieName){
        if (cookies == null || cookies.length == 0){
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst();
    }
}