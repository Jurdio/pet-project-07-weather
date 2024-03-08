package edu.weather.controller;

import edu.weather.domain.service.SessionService;
import edu.weather.domain.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@WebServlet(value = "/home")
public class HomeController extends BaseController {
    private final SessionService sessionService = new SessionService();
    private final UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        webContext.setVariable("username", getCookie(req.getCookies(), "username"));
        webContext.setVariable("sessionId", getCookie(req.getCookies(), "sessionId"));
        templateEngine.process("home", webContext, resp.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
    String getCookie(Cookie[] cookies, String cookie){
       for (Cookie c : cookies){
           if (c.getName().equals(cookie)){
               return c.getValue();
           }
       }
       return "Error";
    }


}
