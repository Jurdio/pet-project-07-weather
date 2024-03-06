package edu.weather.controller;

import edu.weather.domain.service.SessionService;
import edu.weather.domain.service.UserService;
import edu.weather.util.ThymeleafUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@Slf4j
@WebServlet(value = "/sign-in")
public class SignInController extends BaseController {
    private final UserService userService = new UserService();
    private final SessionService sessionService = new SessionService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Ваш код для обробки POST-запиту
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Start GET method -> /sign-in");

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("templates/signIn.html");
        requestDispatcher.forward(req, resp);


        log.info("Finish GET method -> /sign-in");
    }
}
