package edu.weather.controller;

import edu.weather.domain.model.dto.UserDTO;
import edu.weather.domain.service.RegistrationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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

        registrationService.registerUser(userDTO);
        req.setAttribute("username",username);

        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
