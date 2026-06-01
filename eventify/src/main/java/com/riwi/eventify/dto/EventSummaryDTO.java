package com.riwi.eventify.dto;

import java.time.LocalDateTime;

/**
 * Record DTO ligero para listados masivos de eventos.
 * Aplana la información evitando cargar entidades pesadas con colecciones.
 * Diseñado para ser construido directamente desde JPQL con constructor expression.
 */
public record EventSummaryDTO(
    Long id,
    String eventName,
    LocalDateTime date,
    String venueName,
    String city
) {}
