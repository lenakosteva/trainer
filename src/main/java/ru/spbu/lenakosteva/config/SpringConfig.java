package ru.spbu.lenakosteva.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.spbu.lenakosteva.App;

@Configuration
@ComponentScan(basePackageClasses = App.class)
public class SpringConfig {
}
