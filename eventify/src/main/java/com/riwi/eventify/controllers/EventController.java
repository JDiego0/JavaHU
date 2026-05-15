package com.riwi.eventify.controllers;

import com.riwi.eventify.models.Event;
import com.riwi.eventify.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Eventos", description = "Operaciones relacionadas con la gestión de eventos")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    
    @GetMapping
    @Operation(summary = "Obtener todos los eventos", description = "Retorna una lista paginada con todos los eventos registrados. Soporta parámetros: page, size, sort")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de eventos obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay eventos registrados")
    })
    public ResponseEntity<Page<Event>> getAllEvents(
            @Parameter(description = "Número de página (default: 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página (default: 10)") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo de ordenamiento (default: id)") @RequestParam(defaultValue = "id") String sort,
            @Parameter(description = "Dirección de ordenamiento (default: asc)") @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<Event> events = eventService.getAllEvents(pageable);
        return ResponseEntity.ok(events);
    }
    
    @PostMapping
    @Operation(summary = "Crear un nuevo evento", description = "Registra un nuevo evento en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Evento creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos")
    })
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        try {
            Event createdEvent = eventService.createEvent(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener evento por ID", description = "Busca un evento específico por su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evento encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    public ResponseEntity<Event> getEventById(@Parameter(description = "ID del evento a buscar") @PathVariable Long id) {
        return eventService.getEventById(id)
                .map(event -> ResponseEntity.ok(event))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar evento", description = "Elimina un evento del sistema por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Evento eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    public ResponseEntity<Void> deleteEvent(@Parameter(description = "ID del evento a eliminar") @PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar evento", description = "Actualiza un evento existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evento actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos"),
        @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    public ResponseEntity<Event> updateEvent(
            @Parameter(description = "ID del evento a actualizar") @PathVariable Long id,
            @RequestBody Event event) {
        try {
            Event updatedEvent = eventService.updateEvent(id, event);
            return ResponseEntity.ok(updatedEvent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
