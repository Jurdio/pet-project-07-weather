package edu.weather.controller;

import edu.weather.controller.dto.UserDTO;
import edu.weather.domain.model.Session;
import edu.weather.domain.model.User;
import edu.weather.domain.service.*;
import edu.weather.exception.RegistrationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Valid
@WebServlet(value = "/sign-up")
public class SingUpController extends BaseController {
    private final RegistrationService registrationService = new RegistrationService();
    private final SessionService sessionService = new SessionService();
    private final CookieService cookieService = new CookieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Processing sign-up page");
        templateEngine.process("signUp", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmedPassword = req.getParameter("confirmPassword");

        UserDTO userDTO = UserDTO.builder()
                .login(username)
                .password(password).build();

        try {
            int userId = registrationService.registerUserAndReturnIdOrElseThrow(userDTO, confirmedPassword);
            String sessionId = sessionService.createSession(userId).getId().toString();

            log.info("User {} is registered", userDTO.getLogin());
            log.info("Session id is {}", sessionId);

            cookieService.setCookie(resp, "sessionId", sessionId);
            cookieService.setCookie(resp, "username", userDTO.getLogin());

            resp.sendRedirect(req.getContextPath() + "/");
        } catch (RegistrationException e) {
            log.error("Помилка реєстрації: {}", e.getMessage(), e);
            webContext.setVariable("error", e.getMessage());
            templateEngine.process("signUp", webContext, resp.getWriter());
        }
    }

}
