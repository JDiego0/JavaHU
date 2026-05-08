package com.riwi.eventify.services;

import com.riwi.eventify.models.Event;
import com.riwi.eventify.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {
        validateEvent(event);
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    private void validateEvent(Event event) {
        if (event.getName() == null || event.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del evento no puede estar vacío");
        }
        if (event.getDate() == null) {
            throw new IllegalArgumentException("La fecha del evento es obligatoria");
        }
        if (event.getVenue() == null || event.getVenue().trim().isEmpty()) {
            throw new IllegalArgumentException("El lugar del evento es obligatorio");
        }
    }
}
