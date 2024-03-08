package edu.weather.controller;

import edu.weather.domain.model.dto.UserDTO;
import edu.weather.domain.model.entity.Session;
import edu.weather.domain.model.entity.User;
import edu.weather.domain.service.CookieService;
import edu.weather.domain.service.RegistrationService;
import edu.weather.domain.service.SessionService;
import edu.weather.domain.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Valid
@WebServlet(value = "/sign-up")
public class SingUpController extends BaseController {
    private final RegistrationService registrationService = new RegistrationService();
    private final SessionService sessionService = new SessionService();
    private final CookieService cookieService = new CookieService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Processing sign-up page");
        templateEngine.process("signUp", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmedPassword = req.getParameter("confirmedPassword");

        UserDTO userDTO = UserDTO.builder()
                .login(username)
                .password(password).build();

        User user = userService.createUser(userDTO);
        Session session = sessionService.createSession(user.getId());

        log.info("User {} is registered", userDTO.getLogin());
        log.info("Session id is {}", session.getId());

        Cookie cookie = new Cookie("sessionId",session.getId().toString());
        Cookie cookie1 = new Cookie("username", user.getLogin());
        System.out.println(cookie);
        System.out.println(cookie1);

        resp.addCookie(cookie);
        resp.addCookie(cookie1);
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
