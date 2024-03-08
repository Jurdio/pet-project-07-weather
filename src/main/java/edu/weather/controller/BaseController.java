package edu.weather.controller;

import edu.weather.domain.model.entity.Session;
import edu.weather.domain.model.entity.User;
import edu.weather.domain.service.CookieService;
import edu.weather.domain.service.SessionService;
import edu.weather.domain.service.UserService;
import edu.weather.util.ThymeleafUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public abstract class BaseController extends HttpServlet {
    protected TemplateEngine templateEngine;
    protected WebContext webContext;
    private CookieService cookieService;
    private SessionService sessionService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        templateEngine = (TemplateEngine) config.getServletContext().getAttribute("templateEngine");
        cookieService = new CookieService();
        sessionService = new SessionService();
        userService = new UserService();
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        webContext = ThymeleafUtil.getServletContext(req, resp);
//        cookieService.findCookieByName(req.getCookies(), "sessionId")
//                .ifPresent(sessionCookie -> setUserInfoInContext(sessionCookie.getValue()));
        super.service(req, resp);
    }
//    private void setUserInfoInContext(String sessionId){
//        sessionService.getSessionById(sessionId)
//                .filter(sessionService::isSessionExpired)
//                .map(session -> sessionService.getUserBySessionId(sessionId))
//                .ifPresent(user -> {
//                    webContext.setVariable("session_id", sessionId);
//                    webContext.setVariable("userLogin", user.getLogin());
//                });
//
//
//    }






}
