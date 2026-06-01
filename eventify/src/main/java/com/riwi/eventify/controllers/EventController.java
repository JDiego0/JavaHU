package com.riwi.eventify.controllers;

import com.riwi.eventify.dto.EventSummaryDTO;
import com.riwi.eventify.models.Event;
import com.riwi.eventify.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/events")
@Tag(
        name = "Eventos",
        description = "API de eventos con EventSummaryDTO, Slice, busqueda avanzada y borrado logico. "
                + "Los eventos inactivos se excluyen automaticamente mediante @SQLRestriction."
)
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    @Operation(
            summary = "Listado de eventos con DTO ligero",
            description = "Retorna un Slice de EventSummaryDTO construido por JPQL con datos aplanados: "
                    + "id, nombre del evento, fecha, nombre del lugar y ciudad. Usa Slice para evitar COUNT. "
                    + "Ordena siempre por fecha descendente y omite eventos con active=false por @SQLRestriction."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Slice de resumenes obtenido exitosamente",
                    content = @Content(schema = @Schema(implementation = EventSummaryDTO.class)))
    })
    public ResponseEntity<Slice<EventSummaryDTO>> getEventSummaries(
            @Parameter(description = "Numero de pagina, base 0", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de elementos por pagina", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(eventService.getEventSummaries(pageable));
    }

    @GetMapping("/search")
    @Operation(
            summary = "Busqueda avanzada con filtros combinados",
            description = "Filtra por ciudad, categoria, capacidad minima y rango de fechas. "
                    + "Los filtros de texto son parciales e insensibles a mayusculas. "
                    + "Retorna EventSummaryDTO via Slice, ordenado por fecha descendente. "
                    + "El borrado logico es transparente: active=false nunca aparece por @SQLRestriction."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Resultados obtenidos exitosamente",
                    content = @Content(schema = @Schema(implementation = EventSummaryDTO.class)))
    })
    public ResponseEntity<Slice<EventSummaryDTO>> searchEvents(
            @Parameter(description = "Ciudad del venue. Busqueda parcial y case-insensitive.", example = "bog")
            @RequestParam(required = false) String city,
            @Parameter(description = "Nombre de categoria. Busqueda parcial y case-insensitive.", example = "rock")
            @RequestParam(required = false) String category,
            @Parameter(description = "Capacidad minima del venue.", example = "1000")
            @RequestParam(required = false) Integer minCapacity,
            @Parameter(description = "Fecha inicial inclusiva. Formato yyyy-MM-dd'T'HH:mm:ss.", example = "2026-06-01T00:00:00")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Fecha final inclusiva. Formato yyyy-MM-dd'T'HH:mm:ss.", example = "2026-12-31T23:59:59")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Parameter(description = "Numero de pagina, base 0", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de elementos por pagina", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(eventService.searchEvents(city, category, minCapacity, startDate, endDate, pageable));
    }

    @GetMapping("/search/city/{city}")
    @Operation(
            summary = "Buscar eventos por ciudad",
            description = "Filtra por ciudad del venue con busqueda parcial case-insensitive. "
                    + "Retorna EventSummaryDTO ordenado por fecha descendente."
    )
    public ResponseEntity<Slice<EventSummaryDTO>> findByCity(
            @Parameter(description = "Texto de ciudad a buscar", example = "bog")
            @PathVariable String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(eventService.findByCity(city, PageRequest.of(page, size)));
    }

    @GetMapping("/search/category/{categoryName}")
    @Operation(
            summary = "Buscar eventos por categoria",
            description = "Filtra por nombre de categoria usando la relacion ManyToMany events_categories. "
                    + "La busqueda es parcial e insensible a mayusculas."
    )
    public ResponseEntity<Slice<EventSummaryDTO>> findByCategory(
            @Parameter(description = "Texto de categoria a buscar", example = "rock")
            @PathVariable String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(eventService.findByCategoryName(categoryName, PageRequest.of(page, size)));
    }

    @GetMapping("/search/capacity/{minCapacity}")
    @Operation(
            summary = "Buscar eventos por capacidad minima",
            description = "Retorna eventos cuyo venue tenga capacidad mayor o igual al valor indicado."
    )
    public ResponseEntity<Slice<EventSummaryDTO>> findByMinCapacity(
            @Parameter(description = "Capacidad minima del venue", example = "1000")
            @PathVariable Integer minCapacity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(eventService.findByMinCapacity(minCapacity, PageRequest.of(page, size)));
    }

    @GetMapping("/search/dates")
    @Operation(
            summary = "Buscar eventos por rango de fechas",
            description = "Filtra eventos entre startDate y endDate, ambos inclusivos. "
                    + "Retorna EventSummaryDTO ordenado por fecha descendente."
    )
    public ResponseEntity<Slice<EventSummaryDTO>> findByDateBetween(
            @Parameter(description = "Fecha inicial inclusiva", example = "2026-06-01T00:00:00", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Fecha final inclusiva", example = "2026-12-31T23:59:59", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(eventService.findByDateBetween(startDate, endDate, PageRequest.of(page, size)));
    }

    @PostMapping
    @Operation(
            summary = "Crear evento",
            description = "Crea un evento activo con venue obligatorio y categorias opcionales. "
                    + "El servicio resuelve las relaciones por ID y persiste los vinculos ManyToMany."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento creado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(event));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener evento por ID",
            description = "Carga un evento con venue y categorias mediante JOIN FETCH para evitar N+1. "
                    + "Si fue desactivado por soft delete retorna 404 por @SQLRestriction."
    )
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar evento con borrado logico",
            description = "No ejecuta DELETE fisico. Invoca softDelete(), actualiza active=false y "
                    + "@SQLRestriction excluye el registro de consultas posteriores."
    )
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar evento",
            description = "Actualiza un evento activo y resuelve venue/categorias por ID antes de persistir."
    )
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        try {
            return ResponseEntity.ok(eventService.updateEvent(id, event));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
