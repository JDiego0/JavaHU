package com.riwi.eventify.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Eventify API")
                        .version("1.0.0")
                        .description("API para la gestión de eventos y lugares en la plataforma Eventify")
                        .contact(new Contact()
                                .name("Eventify Team")
                                .email("support@eventify.com")))
                .tags(Arrays.asList(
                        new Tag()
                                .name("Eventos")
                                .description("Operaciones relacionadas con la gestión de eventos"),
                        new Tag()
                                .name("Lugares")
                                .description("Operaciones relacionadas con la gestión de lugares")
                ));
    }

    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/api/**")
                .build();
    }
}
