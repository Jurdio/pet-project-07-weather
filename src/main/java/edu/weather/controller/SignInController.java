package edu.weather.controller;

import edu.weather.controller.dto.UserDTO;
import edu.weather.domain.model.Session;
import edu.weather.domain.service.AuthorizationService;
import edu.weather.domain.service.CookieService;
import edu.weather.exception.AuthorizationException;
import edu.weather.exception.RegistrationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(value = "/sign-in")
public class SignInController extends BaseController {
    private final AuthorizationService authorizationService = new AuthorizationService();
    private final CookieService cookieService = new CookieService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Start GET method -> /sign-in");

        log.info("Processing sign-up page");
        templateEngine.process("signIn", webContext, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UserDTO userDTO = UserDTO.builder()
                .login(username)
                .password(password)
                .build();
        try {
            String sessionId = authorizationService.authorizationAndReturnSessionOrElseThrow(userDTO).getId().toString();
            log.info("User {} is registered", userDTO.getLogin());
            log.info("Session id is {}", sessionId);


            cookieService.setCookie(resp, "sessionId", sessionId);
            cookieService.setCookie(resp, "username", userDTO.getLogin());

            resp.sendRedirect(req.getContextPath() + "/");
        } catch (AuthorizationException e) {
            log.error("Помилка реєстрації: {}", e.getMessage(), e);
            webContext.setVariable("error", e.getMessage());
            templateEngine.process("signIn", webContext, resp.getWriter());
        }
    }
}
