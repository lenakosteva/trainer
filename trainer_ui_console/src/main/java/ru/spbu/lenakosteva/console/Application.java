package ru.spbu.lenakosteva.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.spbu.lenakosteva.console.config.SpringConfig;
import ru.spbu.lenakosteva.console.controller.ConsoleController;

@SpringBootApplication
public class Application  implements CommandLineRunner {
    @Autowired
    private ConsoleController controller;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        controller.interactWithUser();
    }
}
