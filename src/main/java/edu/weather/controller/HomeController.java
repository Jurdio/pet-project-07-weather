package edu.weather.controller;

import edu.weather.domain.service.CookieService;
import edu.weather.domain.service.SessionService;
import edu.weather.domain.service.UserService;
import edu.weather.util.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@WebServlet(value = "/home")
public class HomeController extends BaseController {
    private final CookieService cookieService = new CookieService();



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        templateEngine.process("home", webContext, resp.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }



}
