package ru.trainee.creditmanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Credit Management System Api",
                description = "This is description. ", version = "1.0.0",
                contact = @Contact(
                        name = "Nikulin",
                        email = "example@mail.dev",
                        url = "http://web.net"
                )
        )
)
public class SwaggerConfig {

}
