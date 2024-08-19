package br.com.fiap.savvyfix;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Savvyfix openApi", version = "1", description = "API java para o projeto SavvyFix"))
@SpringBootApplication
public class SavvyFixApplication {

    public static void main(String[] args) {
        SpringApplication.run(SavvyFixApplication.class, args);
    }

}
