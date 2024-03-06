package edu.weather.controller.listener;

import edu.weather.util.ThymeleafUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.TemplateEngine;

@WebListener
public class ThymeleafContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        var servletContext = sce.getServletContext();
        var templateEngine = ThymeleafUtil.buildTemplateEngine(servletContext);
        servletContext.setAttribute("templateEngine", templateEngine);
    }
}
