package ru.spbu.lenakosteva.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.spbu.lenakosteva.domain.service.QuestionService;
import ru.spbu.lenakosteva.gui.config.SpringConfig;
import ru.spbu.lenakosteva.gui.controller.MainController;

import javax.swing.*;

public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);
    public static void main(String[] args) {
        logger.info("Начало работы приложения");
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        QuestionService questionService = context.getBean(QuestionService.class);
        SwingUtilities.invokeLater(new MainController(questionService));
        logger.info("Конец работы приложения");
    }
}