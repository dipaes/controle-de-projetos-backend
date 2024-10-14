package com.controleprojetos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.controleprojetos"})
public class ControleDeProjetosBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControleDeProjetosBackendApplication.class, args);
    }
}
