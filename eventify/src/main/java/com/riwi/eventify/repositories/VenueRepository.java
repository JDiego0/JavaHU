package com.riwi.eventify.repositories;

import com.riwi.eventify.models.Venue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class VenueRepository {
    private final List<Venue> venues = new ArrayList<>();
    private Long nextId = 1L;

    public VenueRepository() {
    }

    public Venue save(Venue venue) {
        if (venue.getId() == null) {
            venue.setId(nextId++);
        }
        venues.add(venue);
        return venue;
    }

    public List<Venue> findAll() {
        return new ArrayList<>(venues);
    }

    public Optional<Venue> findById(Long id) {
        return venues.stream()
                .filter(venue -> venue.getId().equals(id))
                .findFirst();
    }

    public void deleteById(Long id) {
        venues.removeIf(venue -> venue.getId().equals(id));
    }

    public void clear() {
        venues.clear();
        nextId = 1L;
    }
}
