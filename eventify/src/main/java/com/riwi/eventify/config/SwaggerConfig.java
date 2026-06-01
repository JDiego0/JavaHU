package com.riwi.eventify.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.models.GroupedOpenApi;
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
                        .version("2.0.0")
                        .description(
                            "API para la gestión de eventos y lugares en la plataforma Eventify.\n\n" +
                            "## Arquitectura de Datos\n" +
                            "- **Modelo relacional**: Event ↔ Venue (ManyToOne), Event ↔ Category (ManyToMany via tabla `events_categories`)\n" +
                            "- **DTO optimizado**: Los listados usan `EventSummaryDTO` (Java Record) para minimizar transferencia de datos\n" +
                            "- **Paginación eficiente**: Uso de `Slice<T>` en lugar de `Page<T>` para evitar consultas COUNT\n\n" +
                            "## Borrado Lógico (Soft Delete)\n" +
                            "Los eventos eliminados no se borran de la base de datos. Se establece `active=false` mediante " +
                            "el método `softDelete()`. Un filtro global `@SQLRestriction(\"active = true\")` garantiza que los " +
                            "registros inactivos **nunca** aparezcan en las consultas.\n\n" +
                            "## Motor de Búsqueda\n" +
                            "Filtros disponibles: ciudad, categoría, capacidad mínima y rango de fechas. " +
                            "Todos los resultados ordenados cronológicamente (fecha descendente).\n\n" +
                            "## Rendimiento\n" +
                            "- N+1 resuelto con `@EntityGraph` y `JOIN FETCH`\n" +
                            "- Auditoría SQL activa: `BasicBinder=TRACE` para trazabilidad de parámetros")
                        .contact(new Contact()
                                .name("Eventify Team")
                                .email("support@eventify.com")))
                .tags(Arrays.asList(
                        new Tag()
                                .name("Eventos")
                                .description("Gestión de eventos con búsqueda avanzada, paginación por Slice, " +
                                             "DTOs Record (EventSummaryDTO), borrado lógico transparente y resolución N+1. " +
                                             "Los listados están ordenados cronológicamente por fecha descendente."),
                        new Tag()
                                .name("Lugares")
                                .description("Gestión de venues/lugares. Cada lugar tiene nombre, dirección, capacidad y ciudad. " +
                                             "Relacionado con eventos mediante ManyToOne.")
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
