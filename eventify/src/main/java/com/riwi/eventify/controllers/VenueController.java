package com.riwi.eventify.controllers;

import com.riwi.eventify.models.Venue;
import com.riwi.eventify.services.VenueService;
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
@RequestMapping("/api/venues")
@Tag(name = "Lugares", description = "Operaciones relacionadas con la gestión de lugares")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }
    
    @GetMapping
    @Operation(summary = "Obtener todos los lugares", description = "Retorna una lista paginada con todos los lugares registrados. Soporta parámetros: page, size, sort")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de lugares obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay lugares registrados")
    })
    public ResponseEntity<Page<Venue>> getAllVenues(
            @Parameter(description = "Número de página (default: 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página (default: 10)") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo de ordenamiento (default: id)") @RequestParam(defaultValue = "id") String sort,
            @Parameter(description = "Dirección de ordenamiento (default: asc)") @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<Venue> venues = venueService.getAllVenues(pageable);
        return ResponseEntity.ok(venues);
    }
    
    @PostMapping
    @Operation(summary = "Crear un nuevo lugar", description = "Registra un nuevo lugar en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Lugar creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos")
    })
    public ResponseEntity<Venue> createVenue(@RequestBody Venue venue) {
        try {
            Venue createdVenue = venueService.createVenue(venue);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVenue);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener lugar por ID", description = "Busca un lugar específico por su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lugar encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Lugar no encontrado")
    })
    public ResponseEntity<Venue> getVenueById(@Parameter(description = "ID del lugar a buscar") @PathVariable Long id) {
        return venueService.getVenueById(id)
                .map(venue -> ResponseEntity.ok(venue))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar lugar", description = "Elimina un lugar del sistema por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Lugar eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Lugar no encontrado")
    })
    public ResponseEntity<Void> deleteVenue(@Parameter(description = "ID del lugar a eliminar") @PathVariable Long id) {
        try {
            venueService.deleteVenue(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar lugar", description = "Actualiza un lugar existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lugar actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos"),
        @ApiResponse(responseCode = "404", description = "Lugar no encontrado")
    })
    public ResponseEntity<Venue> updateVenue(
            @Parameter(description = "ID del lugar a actualizar") @PathVariable Long id,
            @RequestBody Venue venue) {
        try {
            Venue updatedVenue = venueService.updateVenue(id, venue);
            return ResponseEntity.ok(updatedVenue);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
