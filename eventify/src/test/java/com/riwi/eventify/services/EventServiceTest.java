package com.riwi.eventify.services;

import com.riwi.eventify.models.Category;
import com.riwi.eventify.models.Event;
import com.riwi.eventify.models.Venue;
import com.riwi.eventify.repositories.CategoryRepository;
import com.riwi.eventify.repositories.EventRepository;
import com.riwi.eventify.repositories.VenueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private EventService eventService;

    private Event testEvent;
    private Venue testVenue;
    private LocalDateTime testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDateTime.now().plusDays(30);
        testVenue = new Venue("Estadio Nacional", "Av. Jose Diaz s/n", 50000, "Lima");
        testVenue.setId(1L);
        testEvent = new Event("Concierto de Rock", "Gran concierto", testDate, testVenue);
        testEvent.setCategories(new HashSet<>());
    }

    @Test
    void createEvent_ValidEvent_ShouldReturnSavedEvent() {
        // Given
        Event savedEvent = new Event("Concierto de Rock", "Gran concierto", testDate, testVenue);
        savedEvent.setId(1L);
        savedEvent.setActive(true);
        savedEvent.setCategories(new HashSet<>());
        when(venueRepository.findById(1L)).thenReturn(Optional.of(testVenue));
        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        // When
        Event result = eventService.createEvent(testEvent);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Concierto de Rock", result.getName());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void createEvent_EmptyName_ShouldThrowIllegalArgumentException() {
        // Given
        testEvent.setName("");

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> eventService.createEvent(testEvent)
        );
        assertEquals("El nombre del evento no puede estar vacio", exception.getMessage());
        verify(eventRepository, never()).save(any());
    }

    @Test
    void createEvent_NullName_ShouldThrowIllegalArgumentException() {
        // Given
        testEvent.setName(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> eventService.createEvent(testEvent)
        );
        assertEquals("El nombre del evento no puede estar vacio", exception.getMessage());
        verify(eventRepository, never()).save(any());
    }

    @Test
    void createEvent_NullDate_ShouldThrowIllegalArgumentException() {
        // Given
        testEvent.setDate(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> eventService.createEvent(testEvent)
        );
        assertEquals("La fecha del evento es obligatoria", exception.getMessage());
        verify(eventRepository, never()).save(any());
    }

    @Test
    void createEvent_NullVenue_ShouldThrowIllegalArgumentException() {
        // Given
        testEvent.setVenue(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> eventService.createEvent(testEvent)
        );
        assertEquals("El lugar del evento es obligatorio", exception.getMessage());
        verify(eventRepository, never()).save(any());
    }

    @Test
    void getAllEvents_ShouldReturnAllEvents() {
        // Given
        Venue venue1 = new Venue("Lugar 1", "Dir 1", 100, "Lima");
        venue1.setId(1L);
        Venue venue2 = new Venue("Lugar 2", "Dir 2", 200, "Callao");
        venue2.setId(2L);
        Event e1 = new Event("Evento 1", "Descripción 1", testDate, venue1);
        e1.setId(1L);
        Event e2 = new Event("Evento 2", "Descripción 2", testDate, venue2);
        e2.setId(2L);
        List<Event> events = Arrays.asList(e1, e2);
        when(eventRepository.findAllWithVenueAndCategories()).thenReturn(events);

        // When
        List<Event> result = eventService.getAllEvents();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Evento 1", result.get(0).getName());
        verify(eventRepository, times(1)).findAllWithVenueAndCategories();
    }

    @Test
    void getEventById_ExistingId_ShouldReturnEvent() {
        // Given
        Event event = new Event("Evento 1", "Descripción 1", testDate, testVenue);
        event.setId(1L);
        when(eventRepository.findByIdWithRelations(1L)).thenReturn(Optional.of(event));

        // When
        Optional<Event> result = eventService.getEventById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Evento 1", result.get().getName());
        verify(eventRepository, times(1)).findByIdWithRelations(1L);
    }

    @Test
    void getEventById_NonExistingId_ShouldReturnEmpty() {
        // Given
        when(eventRepository.findByIdWithRelations(999L)).thenReturn(Optional.empty());

        // When
        Optional<Event> result = eventService.getEventById(999L);

        // Then
        assertFalse(result.isPresent());
        verify(eventRepository, times(1)).findByIdWithRelations(999L);
    }

    @Test
    void deleteEvent_ValidId_ShouldSoftDelete() {
        // Given
        Event event = new Event("Evento 1", "Descripción", testDate, testVenue);
        event.setId(1L);
        event.setActive(true);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // When
        eventService.deleteEvent(1L);

        // Then
        assertFalse(event.getActive()); // soft delete sets active=false
        verify(eventRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void deleteEvent_NonExistingId_ShouldThrowRuntimeException() {
        // Given
        when(eventRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> eventService.deleteEvent(999L)
        );
        assertEquals("Evento no encontrado con ID: 999", exception.getMessage());
        verify(eventRepository, times(1)).findById(999L);
        verify(eventRepository, never()).save(any());
    }

    @Test
    void updateEvent_ValidId_ShouldReturnUpdatedEvent() {
        // Given
        Event updatedEvent = new Event("Evento Actualizado", "Nueva Descripción", testDate, testVenue);
        updatedEvent.setCategories(new HashSet<>());
        Event savedEvent = new Event("Evento Actualizado", "Nueva Descripción", testDate, testVenue);
        savedEvent.setId(1L);
        savedEvent.setActive(true);
        when(eventRepository.existsById(1L)).thenReturn(true);
        when(venueRepository.findById(1L)).thenReturn(Optional.of(testVenue));
        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        // When
        Event result = eventService.updateEvent(1L, updatedEvent);

        // Then
        assertNotNull(result);
        assertEquals("Evento Actualizado", result.getName());
        verify(eventRepository, times(1)).existsById(1L);
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void updateEvent_NonExistingId_ShouldThrowRuntimeException() {
        // Given
        Event updatedEvent = new Event("Evento Actualizado", "Nueva Descripción", testDate, testVenue);
        when(eventRepository.existsById(999L)).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> eventService.updateEvent(999L, updatedEvent)
        );
        assertEquals("Evento no encontrado con ID: 999", exception.getMessage());
        verify(eventRepository, times(1)).existsById(999L);
        verify(eventRepository, never()).save(any());
    }
}
