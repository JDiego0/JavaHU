package com.riwi.eventify.services;

import com.riwi.eventify.models.Venue;
import com.riwi.eventify.repositories.VenueRepository;
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

    public Optional<Venue> getVenueById(Long id) {
        return venueRepository.findById(id);
    }

    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
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
