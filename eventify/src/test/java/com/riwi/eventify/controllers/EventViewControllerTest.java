package com.riwi.eventify.controllers;

import com.riwi.eventify.models.Event;
import com.riwi.eventify.models.Venue;
import com.riwi.eventify.services.EventService;
import com.riwi.eventify.services.VenueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventViewController.class)
public class EventViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private VenueService venueService;

    private Event testEvent;
    private Venue testVenue;

    @BeforeEach
    void setUp() {
        testEvent = new Event();
        testEvent.setId(1L);
        testEvent.setName("Evento de prueba");
        testEvent.setDescription("Descripción de prueba");
        testEvent.setDate(LocalDateTime.now().plusDays(1));
        testEvent.setVenue("Lugar de prueba");
        testVenue = new Venue(1L, "Lugar de prueba", "Direccion de prueba", 100);
        when(venueService.getAllVenues()).thenReturn(Arrays.asList(testVenue));
    }

    @Test
    void testListEvents_ReturnsViewWithEvents() throws Exception {
        List<Event> events = Arrays.asList(testEvent);
        when(eventService.getAllEvents()).thenReturn(events);

        mockMvc.perform(get("/admin/events"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/list"))
                .andExpect(model().attributeExists("events"))
                .andExpect(model().attribute("events", events));

        verify(eventService, times(1)).getAllEvents();
    }

    @Test
    void testListEvents_WithSuccessMessage() throws Exception {
        List<Event> events = Arrays.asList(testEvent);
        when(eventService.getAllEvents()).thenReturn(events);

        mockMvc.perform(get("/admin/events")
                        .param("success", "Evento creado exitosamente"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/list"))
                .andExpect(model().attributeExists("events"))
                .andExpect(model().attributeExists("successMessage"))
                .andExpect(model().attribute("successMessage", "Evento creado exitosamente"));
    }

    @Test
    void testShowCreateForm_ReturnsViewWithNewEvent() throws Exception {
        mockMvc.perform(get("/admin/events/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/form"))
                .andExpect(model().attributeExists("event"));
    }

    @Test
    void testShowEditForm_WithValidId_ReturnsViewWithEvent() throws Exception {
        when(eventService.getEventById(1L)).thenReturn(Optional.of(testEvent));

        mockMvc.perform(get("/admin/events/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("events/form"))
                .andExpect(model().attributeExists("event"))
                .andExpect(model().attribute("event", testEvent));

        verify(eventService, times(1)).getEventById(1L);
    }

    @Test
    void testShowEditForm_WithInvalidId_RedirectsToList() throws Exception {
        when(eventService.getEventById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/admin/events/edit/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/events"));

        verify(eventService, times(1)).getEventById(999L);
    }

    @Test
    void testSaveEvent_NewEvent_RedirectsToList() throws Exception {
        when(eventService.createEvent(any(Event.class))).thenReturn(testEvent);

        mockMvc.perform(post("/admin/events/save")
                        .param("name", "Nuevo evento")
                        .param("description", "Descripción")
                        .param("date", LocalDateTime.now().plusDays(1).toString())
                        .param("venue", "Lugar"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/events"));

        verify(eventService, times(1)).createEvent(any(Event.class));
    }

    @Test
    void testSaveEvent_ExistingEvent_RedirectsToList() throws Exception {
        when(eventService.updateEvent(eq(1L), any(Event.class))).thenReturn(testEvent);

        mockMvc.perform(post("/admin/events/save")
                        .param("id", "1")
                        .param("name", "Evento actualizado")
                        .param("description", "Descripción actualizada")
                        .param("date", LocalDateTime.now().plusDays(1).toString())
                        .param("venue", "Lugar"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/events"));

        verify(eventService, times(1)).updateEvent(eq(1L), any(Event.class));
    }

    @Test
    void testDeleteEvent_WithValidId_RedirectsToList() throws Exception {
        doNothing().when(eventService).deleteEvent(1L);

        mockMvc.perform(post("/admin/events/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/events"));

        verify(eventService, times(1)).deleteEvent(1L);
    }
}
