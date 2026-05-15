package com.riwi.eventify.services;

import com.riwi.eventify.models.Event;
import com.riwi.eventify.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event testEvent;
    private LocalDateTime testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDateTime.now().plusDays(30);
        testEvent = new Event("Concierto de Rock", "Gran concierto", testDate, "Estadio Nacional");
    }

    @Test
    void createEvent_ValidEvent_ShouldReturnSavedEvent() {
        // Given
        Event savedEvent = new Event(1L, "Concierto de Rock", "Gran concierto", testDate, "Estadio Nacional");
        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        // When
        Event result = eventService.createEvent(testEvent);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Concierto de Rock", result.getName());
        verify(eventRepository, times(1)).save(testEvent);
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
        assertEquals("El nombre del evento no puede estar vacío", exception.getMessage());
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
        assertEquals("El nombre del evento no puede estar vacío", exception.getMessage());
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
    void createEvent_EmptyVenue_ShouldThrowIllegalArgumentException() {
        // Given
        testEvent.setVenue("");

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
        List<Event> events = Arrays.asList(
                new Event(1L, "Evento 1", "Descripción 1", testDate, "Lugar 1"),
                new Event(2L, "Evento 2", "Descripción 2", testDate, "Lugar 2")
        );
        when(eventRepository.findAll()).thenReturn(events);

        // When
        List<Event> result = eventService.getAllEvents();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Evento 1", result.get(0).getName());
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void getEventById_ExistingId_ShouldReturnEvent() {
        // Given
        Event event = new Event(1L, "Evento 1", "Descripción 1", testDate, "Lugar 1");
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        // When
        Optional<Event> result = eventService.getEventById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Evento 1", result.get().getName());
        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    void getEventById_NonExistingId_ShouldReturnEmpty() {
        // Given
        when(eventRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Event> result = eventService.getEventById(999L);

        // Then
        assertFalse(result.isPresent());
        verify(eventRepository, times(1)).findById(999L);
    }

    @Test
    void deleteEvent_ValidId_ShouldCallRepository() {
        // Given
        when(eventRepository.existsById(1L)).thenReturn(true);

        // When
        eventService.deleteEvent(1L);

        // Then
        verify(eventRepository, times(1)).existsById(1L);
        verify(eventRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteEvent_NonExistingId_ShouldThrowRuntimeException() {
        // Given
        when(eventRepository.existsById(999L)).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> eventService.deleteEvent(999L)
        );
        assertEquals("Evento no encontrado con ID: 999", exception.getMessage());
        verify(eventRepository, times(1)).existsById(999L);
        verify(eventRepository, never()).deleteById(any());
    }

    @Test
    void updateEvent_ValidId_ShouldReturnUpdatedEvent() {
        // Given
        Event existingEvent = new Event(1L, "Evento Antiguo", "Descripción", testDate, "Lugar");
        Event updatedEvent = new Event(1L, "Evento Actualizado", "Nueva Descripción", testDate, "Nuevo Lugar");
        when(eventRepository.existsById(1L)).thenReturn(true);
        when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);

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
        Event updatedEvent = new Event(1L, "Evento Actualizado", "Nueva Descripción", testDate, "Nuevo Lugar");
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
