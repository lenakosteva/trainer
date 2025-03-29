package ru.spbu.lenakosteva.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import ru.spbu.lenakosteva.domain.service.QuestionService;
import ru.spbu.lenakosteva.gui.controller.MainController;

import javax.swing.*;

@SpringBootApplication
public class Application  {
    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Application.class)
                .headless(false).run(args);
        logger.info("Начало работы приложения");
        QuestionService questionService = ctx.getBean(QuestionService.class);
        SwingUtilities.invokeLater(new MainController(questionService));
        logger.info("Конец работы приложения");
    }
}