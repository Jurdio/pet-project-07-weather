package edu.weather.domain.service;

import edu.weather.domain.model.entity.Session;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Optional;

public class CookieService {
    public Optional<Cookie> findCookieByName(Cookie[] cookies, String cookieName) {
        if (cookies == null || cookies.length == 0) {
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst();
}

    public void setCookie(HttpServletResponse response, Session session) {
        Cookie cookie = new Cookie("sessionId", session.getId().toString());
        response.addCookie(cookie);
    }
}
