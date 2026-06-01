package com.riwi.eventify.repositories;

import com.riwi.eventify.dto.EventSummaryDTO;
import com.riwi.eventify.models.Event;
import com.riwi.eventify.models.Venue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EventRepository eventRepository;

    private Venue venue1;
    private Venue venue2;
    private Event event1;
    private Event event2;
    private Event event3;

    @BeforeEach
    void setUp() {
        venue1 = entityManager.persist(new Venue("Centro Test", "Av. Test 123", 5000, "Medellin"));
        venue2 = entityManager.persist(new Venue("Estadio Test", "Calle Test 456", 50000, "Cali"));

        event1 = new Event("Conferencia Tech Test", "Conferencia sobre tecnologia",
                LocalDateTime.of(2026, 6, 15, 10, 0), venue1);
        event1.setActive(true);
        event1.setCategories(new HashSet<>());

        event2 = new Event("Concierto Rock Test", "Concierto de rock en vivo",
                LocalDateTime.of(2026, 7, 20, 20, 0), venue2);
        event2.setActive(true);
        event2.setCategories(new HashSet<>());

        event3 = new Event("Workshop Java Test", "Taller de programacion Java",
                LocalDateTime.of(2026, 8, 10, 9, 0), venue1);
        event3.setActive(true);
        event3.setCategories(new HashSet<>());
    }

    @Test
    void whenSaveEvent_thenEventIsPersisted() {
        Event savedEvent = eventRepository.save(event1);

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
    void whenFindAll_thenReturnActiveEventsIncludingSeedData() {
        entityManager.persist(event1);
        entityManager.persist(event2);
        entityManager.persist(event3);

        List<Event> events = eventRepository.findAll();

        assertThat(events).extracting(Event::getName)
                .contains(event1.getName(), event2.getName(), event3.getName())
                .doesNotContain("Evento Inactivo Test");
        assertThat(events.size()).isGreaterThanOrEqualTo(203);
    }

    @Test
    void whenFindAllWithPagination_thenReturnPageWithoutAssumingEmptyFlywaySeed() {
        entityManager.persist(event1);
        entityManager.persist(event2);
        entityManager.persist(event3);

        Pageable pageable = PageRequest.of(0, 2, Sort.by("name").ascending());
        Page<Event> eventPage = eventRepository.findAll(pageable);

        assertThat(eventPage.getContent()).hasSize(2);
        assertThat(eventPage.getTotalElements()).isGreaterThanOrEqualTo(203);
        assertThat(eventPage.getTotalPages()).isGreaterThan(1);
    }

    @Test
    void whenFindByVenue_thenReturnEventsAtVenue() {
        entityManager.persist(event1);
        entityManager.persist(event2);
        entityManager.persist(event3);

        List<Event> events = eventRepository.findByVenueOrderByDateDesc(venue1);

        assertThat(events).extracting(Event::getName)
                .containsExactly(event3.getName(), event1.getName());
    }

    @Test
    void whenDeleteById_thenEventIsRemoved() {
        Event savedEvent = entityManager.persist(event1);

        eventRepository.deleteById(savedEvent.getId());
        entityManager.flush();
        entityManager.clear();

        assertThat(eventRepository.findById(savedEvent.getId())).isEmpty();
    }

    @Test
    void whenEventIsInactive_thenSqlRestrictionHidesIt() {
        Event inactive = new Event("Evento Inactivo Test", "No debe aparecer",
                LocalDateTime.of(2026, 9, 1, 10, 0), venue1);
        inactive.setActive(false);
        inactive.setCategories(new HashSet<>());
        entityManager.persist(inactive);
        entityManager.flush();
        entityManager.clear();

        assertThat(eventRepository.findAll())
                .extracting(Event::getName)
                .doesNotContain("Evento Inactivo Test");
    }

    @Test
    void whenSearchByRockCategoryOrBogCity_thenFindAcceptanceSeed() {
        Pageable pageable = PageRequest.of(0, 300);

        Slice<EventSummaryDTO> byCategory = eventRepository.findByCategoryName("rock", pageable);
        Slice<EventSummaryDTO> byCity = eventRepository.findByCity("bog", pageable);

        assertThat(byCategory.getContent())
                .extracting(EventSummaryDTO::eventName)
                .contains("Concierto de ROCK");
        assertThat(byCity.getContent())
                .extracting(EventSummaryDTO::eventName)
                .contains("Concierto de ROCK");
    }

    @Test
    void whenExistsById_thenReturnTrue() {
        Event savedEvent = entityManager.persist(event1);

        assertThat(eventRepository.existsById(savedEvent.getId())).isTrue();
    }

    @Test
    void whenExistsByIdWithNonExistentId_thenReturnFalse() {
        assertThat(eventRepository.existsById(999999L)).isFalse();
    }
}
