package ru.nikitos.cars.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.nikitos.cars.entity.Car;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ComponentScan("ru.nikitos.cars")
public class AppConfig {

    @Bean
    @Scope("singleton")
    public Connection connection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nikitos", "root", "nikita07032005");
        return connection;
    }


}

