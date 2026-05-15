package com.riwi.eventify.services;

import com.riwi.eventify.models.Venue;
import com.riwi.eventify.repositories.VenueRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {
    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Venue createVenue(Venue venue) {
        validateVenue(venue);
        return venueRepository.save(venue);
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public Page<Venue> getAllVenues(Pageable pageable) {
        return venueRepository.findAll(pageable);
    }

    public Optional<Venue> getVenueById(Long id) {
        return venueRepository.findById(id);
    }

    public void deleteVenue(Long id) {
        if (!venueRepository.existsById(id)) {
            throw new RuntimeException("Lugar no encontrado con ID: " + id);
        }
        venueRepository.deleteById(id);
    }

    public Venue updateVenue(Long id, Venue venue) {
        if (!venueRepository.existsById(id)) {
            throw new RuntimeException("Lugar no encontrado con ID: " + id);
        }
        validateVenue(venue);
        venue.setId(id);
        return venueRepository.save(venue);
    }

    private void validateVenue(Venue venue) {
        if (venue.getName() == null || venue.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del lugar no puede estar vacío");
        }
        if (venue.getAddress() == null || venue.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección del lugar es obligatoria");
        }
        if (venue.getCapacity() == null || venue.getCapacity() <= 0) {
            throw new IllegalArgumentException("La capacidad del lugar debe ser mayor a cero");
        }
    }
}
