package edu.weather.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.http.HttpHeaders;
import java.util.Arrays;
import java.util.Objects;

@WebServlet(value = "/styles/css/*")
public class ResourceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getPathInfo().split("/")[1];

        // Отримуємо вміст файлу з ресурсів у вигляді потоку
        InputStream is = ResourceController.class.getClassLoader().getResourceAsStream("/static/css/" + code);

        // Конвертуємо потік в рядок
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bf.readLine()) != null) {
            sb.append(line).append("\n");
        }

        // Встановлюємо HTTP-заголовки
        resp.setContentType("text/css; charset=utf-8");

        // Відправляємо відповідь
        resp.getWriter().write(sb.toString());
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
