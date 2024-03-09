package edu.weather.controller;

import edu.weather.domain.model.entity.Session;
import edu.weather.domain.model.entity.User;
import edu.weather.domain.service.CookieService;
import edu.weather.domain.service.SessionService;
import edu.weather.domain.service.UserService;
import edu.weather.util.ThymeleafUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.util.UUID;

public abstract class BaseController extends HttpServlet {
    protected TemplateEngine templateEngine;
    protected WebContext webContext;
    private final CookieService cookieService = new CookieService();
    private final SessionService sessionService = new SessionService();
    private final UserService userService = new UserService();


    @Override
    public void init(ServletConfig config) throws ServletException {
        templateEngine = (TemplateEngine) config.getServletContext().getAttribute("templateEngine");
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        webContext = ThymeleafUtil.getServletContext(req, resp);
        cookieService.findCookieByName(req.getCookies(), "sessionId")
                .ifPresent(cookie -> {
                    handleSessionCookie(cookie.getValue());
                });
        super.service(req, resp);
    }

    private void handleSessionCookie(String sessionId) {
        System.out.println("Contoler"+sessionId);
       try {
           Session session = sessionService.getSessionById(UUID.fromString(sessionId));
           System.out.println(session.getId().toString());
           webContext.setVariable("sessionId",session.getId().toString());
           webContext.setVariable("username",userService.getUserById(session.getUserId()).orElseThrow().getLogin());
           webContext.setVariable("loggedIn", true);
       } catch (Exception e){
           System.out.println("Zalypa!!!!!!");
       }
    }


}
