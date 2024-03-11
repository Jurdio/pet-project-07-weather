package edu.weather.controller;

import edu.weather.controller.dto.UserDTO;
import edu.weather.domain.model.Session;
import edu.weather.domain.service.AuthorizationService;
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

        Session session = authorizationService.getAuthorizationByLoginAndThenReturnSession(userDTO);
        log.info("User {} is registered", userDTO.getLogin());
        log.info("Session id is {}", session.getId());

        Cookie cookie = new Cookie("sessionId",session.getId().toString());
        Cookie cookie1 = new Cookie("username", userDTO.getLogin());
        System.out.println(cookie);
        System.out.println(cookie1);

        resp.addCookie(cookie);
        resp.addCookie(cookie1);
        resp.sendRedirect(req.getContextPath() + "/home");


    }


}
