package ru.spbu.lenakosteva.spring.jdbc.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.sql.DataSource;

@Configuration
public class DbConfig {
    @Value("${driverClassName}") private String driverClassName;
    @Value("${url}") private String url;
    @Value("${user_name}") private String userName;
    @Value("${password}") private String password;

    @Bean
    @Lazy
    public DataSource dataSource() {
        try {
            BasicDataSource dataSource
                    = new BasicDataSource();
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(url);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);
            return dataSource;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
