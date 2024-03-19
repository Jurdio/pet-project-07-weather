package edu.weather.controller;

import edu.weather.domain.service.CookieService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/sign-out")
public class SignOutController extends BaseController {
    private final CookieService cookieService = new CookieService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cookieService.findCookieByName(req.getCookies(), "sessionId")
                .ifPresent(cookie -> {
                    cookieService.clearCookie(resp, cookie);
                });
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
