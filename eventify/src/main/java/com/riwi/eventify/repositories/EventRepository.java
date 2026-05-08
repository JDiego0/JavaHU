package com.riwi.eventify.repositories;

import com.riwi.eventify.models.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EventRepository {
    private final List<Event> events = new ArrayList<>();
    private Long nextId = 1L;

    public EventRepository() {
    }

    public Event save(Event event) {
        if (event.getId() == null) {
            event.setId(nextId++);
        }
        events.add(event);
        return event;
    }

    public List<Event> findAll() {
        return new ArrayList<>(events);
    }

    public Optional<Event> findById(Long id) {
        return events.stream()
                .filter(event -> event.getId().equals(id))
                .findFirst();
    }

    public void deleteById(Long id) {
        events.removeIf(event -> event.getId().equals(id));
    }

    public void clear() {
        events.clear();
        nextId = 1L;
    }
}
