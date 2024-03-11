package edu.weather.domain.service;

import edu.weather.domain.model.Session;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CookieService {
    private final SessionService sessionService = new SessionService();
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
    public void clearCookie(HttpServletResponse response, Cookie cookie){
        sessionService.deleteSessionById(UUID.fromString(cookie.getValue()));
        cookie.setValue("");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
