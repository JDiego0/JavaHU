package com.riwi.eventify.services;

import com.riwi.eventify.dto.EventSummaryDTO;
import com.riwi.eventify.models.Category;
import com.riwi.eventify.models.Event;
import com.riwi.eventify.models.Venue;
import com.riwi.eventify.repositories.CategoryRepository;
import com.riwi.eventify.repositories.EventRepository;
import com.riwi.eventify.repositories.VenueRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final CategoryRepository categoryRepository;

    public EventService(EventRepository eventRepository,
                        VenueRepository venueRepository,
                        CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Event createEvent(Event event) {
        validateEvent(event);
        resolveVenue(event);
        resolveCategories(event);
        event.setActive(true);
        return eventRepository.save(event);
    }

    @Transactional(readOnly = true)
    public Slice<EventSummaryDTO> getEventSummaries(Pageable pageable) {
        return eventRepository.findAllEventSummaries(pageable);
    }

    @Transactional(readOnly = true)
    public List<Event> getAllEvents() {
        return eventRepository.findAllWithVenueAndCategories();
    }

    @Transactional(readOnly = true)
    public Slice<EventSummaryDTO> searchEventsForAdmin(String city, String category, Pageable pageable) {
        return eventRepository.searchEventSummariesForAdmin(normalizeFilter(city), normalizeFilter(category), pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findByIdWithRelations(id);
    }

    @Transactional(readOnly = true)
    public Slice<EventSummaryDTO> findByCity(String city, Pageable pageable) {
        return eventRepository.findByCity(city, pageable);
    }

    @Transactional(readOnly = true)
    public Slice<EventSummaryDTO> findByCategoryName(String categoryName, Pageable pageable) {
        return eventRepository.findByCategoryName(categoryName, pageable);
    }

    @Transactional(readOnly = true)
    public Slice<EventSummaryDTO> findByMinCapacity(Integer minCapacity, Pageable pageable) {
        return eventRepository.findByMinCapacity(minCapacity, pageable);
    }

    @Transactional(readOnly = true)
    public Slice<EventSummaryDTO> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return eventRepository.findByDateBetween(startDate, endDate, pageable);
    }

    @Transactional(readOnly = true)
    public Slice<EventSummaryDTO> searchEvents(String city,
                                               String categoryName,
                                               Integer minCapacity,
                                               LocalDateTime startDate,
                                               LocalDateTime endDate,
                                               Pageable pageable) {
        return eventRepository.searchEvents(
                normalizeFilter(city),
                normalizeFilter(categoryName),
                minCapacity,
                startDate,
                endDate,
                pageable);
    }

    @Transactional
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + id));
        event.softDelete();
        eventRepository.save(event);
    }

    @Transactional
    public Event updateEvent(Long id, Event event) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Evento no encontrado con ID: " + id);
        }
        validateEvent(event);
        resolveVenue(event);
        resolveCategories(event);
        event.setId(id);
        event.setActive(true);
        return eventRepository.save(event);
    }

    private void resolveVenue(Event event) {
        if (event.getVenue() != null && event.getVenue().getId() != null) {
            Venue venue = venueRepository.findById(event.getVenue().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Lugar no encontrado con ID: " + event.getVenue().getId()));
            event.setVenue(venue);
        }
    }

    private void resolveCategories(Event event) {
        if (event.getCategories() != null && !event.getCategories().isEmpty()) {
            Set<Category> resolvedCategories = new HashSet<>();
            for (Category category : event.getCategories()) {
                if (category.getId() != null) {
                    Category resolved = categoryRepository.findById(category.getId())
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "Categoria no encontrada con ID: " + category.getId()));
                    resolvedCategories.add(resolved);
                }
            }
            event.setCategories(resolvedCategories);
        }
    }

    private void validateEvent(Event event) {
        if (event.getName() == null || event.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del evento no puede estar vacio");
        }
        if (event.getDate() == null) {
            throw new IllegalArgumentException("La fecha del evento es obligatoria");
        }
        if (event.getVenue() == null || event.getVenue().getId() == null) {
            throw new IllegalArgumentException("El lugar del evento es obligatorio");
        }
    }

    private String normalizeFilter(String value) {
        return (value != null && !value.trim().isEmpty()) ? value.trim() : null;
    }
}
