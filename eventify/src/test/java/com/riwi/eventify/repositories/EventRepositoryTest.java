package com.riwi.eventify.repositories;

import com.riwi.eventify.models.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EventRepository eventRepository;

    private Event event1;
    private Event event2;
    private Event event3;

    @BeforeEach
    void setUp() {
        event1 = new Event("Conferencia Tech", "Conferencia sobre tecnología", LocalDateTime.of(2026, 6, 15, 10, 0), "Centro de Convenciones");
        event2 = new Event("Concierto Rock", "Concierto de rock en vivo", LocalDateTime.of(2026, 7, 20, 20, 0), "Estadio Nacional");
        event3 = new Event("Workshop Java", "Taller de programación Java", LocalDateTime.of(2026, 8, 10, 9, 0), "Sala de Conferencias");
    }

    @Test
    void whenSaveEvent_thenEventIsPersisted() {
        Event savedEvent = eventRepository.save(event1);
        
        assertThat(savedEvent).isNotNull();
        assertThat(savedEvent.getId()).isNotNull();
        assertThat(savedEvent.getName()).isEqualTo(event1.getName());
    }

    @Test
    void whenFindById_thenReturnEvent() {
        Event savedEvent = entityManager.persist(event1);
        
        Event foundEvent = eventRepository.findById(savedEvent.getId()).orElse(null);
        
        assertThat(foundEvent).isNotNull();
        assertThat(foundEvent.getName()).isEqualTo(event1.getName());
    }

    @Test
    void whenFindAll_thenReturnAllEvents() {
        entityManager.persist(event1);
        entityManager.persist(event2);
        entityManager.persist(event3);
        
        List<Event> events = eventRepository.findAll();
        
        assertThat(events).hasSize(3);
        assertThat(events).extracting(Event::getName).containsExactlyInAnyOrder(
            event1.getName(), event2.getName(), event3.getName()
        );
    }

    @Test
    void whenFindAllWithPagination_thenReturnPage() {
        entityManager.persist(event1);
        entityManager.persist(event2);
        entityManager.persist(event3);
        
        Pageable pageable = PageRequest.of(0, 2, Sort.by("name").ascending());
        Page<Event> eventPage = eventRepository.findAll(pageable);
        
        assertThat(eventPage.getContent()).hasSize(2);
        assertThat(eventPage.getTotalElements()).isEqualTo(3);
        assertThat(eventPage.getTotalPages()).isEqualTo(2);
    }

    @Test
    void whenFindByNameContainingIgnoreCase_thenReturnMatchingEvents() {
        entityManager.persist(event1);
        entityManager.persist(event2);
        entityManager.persist(event3);
        
        List<Event> events = eventRepository.findByNameContainingIgnoreCase("con");
        
        assertThat(events).hasSize(2);
        assertThat(events).extracting(Event::getName).containsExactlyInAnyOrder(
            event1.getName(), event2.getName()
        );
    }

    @Test
    void whenFindByVenue_thenReturnEventsAtVenue() {
        entityManager.persist(event1);
        entityManager.persist(event2);
        entityManager.persist(event3);
        
        List<Event> events = eventRepository.findByVenue("Centro de Convenciones");
        
        assertThat(events).hasSize(1);
        assertThat(events.get(0).getName()).isEqualTo(event1.getName());
    }

    @Test
    void whenDeleteById_thenEventIsRemoved() {
        Event savedEvent = entityManager.persist(event1);
        
        eventRepository.deleteById(savedEvent.getId());
        
        Event deletedEvent = eventRepository.findById(savedEvent.getId()).orElse(null);
        assertThat(deletedEvent).isNull();
    }

    @Test
    void whenExistsById_thenReturnTrue() {
        Event savedEvent = entityManager.persist(event1);
        
        boolean exists = eventRepository.existsById(savedEvent.getId());
        
        assertThat(exists).isTrue();
    }

    @Test
    void whenExistsByIdWithNonExistentId_thenReturnFalse() {
        boolean exists = eventRepository.existsById(9999L);
        
        assertThat(exists).isFalse();
    }
}
