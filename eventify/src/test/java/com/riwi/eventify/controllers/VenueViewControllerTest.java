package com.riwi.eventify.controllers;

import com.riwi.eventify.models.Venue;
import com.riwi.eventify.services.VenueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VenueViewController.class)
public class VenueViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VenueService venueService;

    private Venue testVenue;

    @BeforeEach
    void setUp() {
        testVenue = new Venue();
        testVenue.setId(1L);
        testVenue.setName("Lugar de prueba");
        testVenue.setAddress("Dirección de prueba");
        testVenue.setCapacity(100);
    }

    @Test
    void testListVenues_ReturnsViewWithVenues() throws Exception {
        List<Venue> venues = Arrays.asList(testVenue);
        when(venueService.getAllVenues()).thenReturn(venues);

        mockMvc.perform(get("/admin/venues"))
                .andExpect(status().isOk())
                .andExpect(view().name("venues/list"))
                .andExpect(model().attributeExists("venues"))
                .andExpect(model().attribute("venues", venues));

        verify(venueService, times(1)).getAllVenues();
    }

    @Test
    void testListVenues_WithSuccessMessage() throws Exception {
        List<Venue> venues = Arrays.asList(testVenue);
        when(venueService.getAllVenues()).thenReturn(venues);

        mockMvc.perform(get("/admin/venues")
                        .param("success", "Lugar creado exitosamente"))
                .andExpect(status().isOk())
                .andExpect(view().name("venues/list"))
                .andExpect(model().attributeExists("venues"))
                .andExpect(model().attributeExists("successMessage"))
                .andExpect(model().attribute("successMessage", "Lugar creado exitosamente"));
    }

    @Test
    void testShowCreateForm_ReturnsViewWithNewVenue() throws Exception {
        mockMvc.perform(get("/admin/venues/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("venues/form"))
                .andExpect(model().attributeExists("venue"));
    }

    @Test
    void testShowEditForm_WithValidId_ReturnsViewWithVenue() throws Exception {
        when(venueService.getVenueById(1L)).thenReturn(Optional.of(testVenue));

        mockMvc.perform(get("/admin/venues/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("venues/form"))
                .andExpect(model().attributeExists("venue"))
                .andExpect(model().attribute("venue", testVenue));

        verify(venueService, times(1)).getVenueById(1L);
    }

    @Test
    void testShowEditForm_WithInvalidId_RedirectsToList() throws Exception {
        when(venueService.getVenueById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/admin/venues/edit/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/venues"));

        verify(venueService, times(1)).getVenueById(999L);
    }

    @Test
    void testSaveVenue_NewVenue_RedirectsToList() throws Exception {
        when(venueService.createVenue(any(Venue.class))).thenReturn(testVenue);

        mockMvc.perform(post("/admin/venues/save")
                        .param("name", "Nuevo lugar")
                        .param("address", "Dirección")
                        .param("capacity", "50"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/venues"));

        verify(venueService, times(1)).createVenue(any(Venue.class));
    }

    @Test
    void testSaveVenue_ExistingVenue_RedirectsToList() throws Exception {
        when(venueService.updateVenue(eq(1L), any(Venue.class))).thenReturn(testVenue);

        mockMvc.perform(post("/admin/venues/save")
                        .param("id", "1")
                        .param("name", "Lugar actualizado")
                        .param("address", "Dirección actualizada")
                        .param("capacity", "150"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/venues"));

        verify(venueService, times(1)).updateVenue(eq(1L), any(Venue.class));
    }

    @Test
    void testDeleteVenue_WithValidId_RedirectsToList() throws Exception {
        doNothing().when(venueService).deleteVenue(1L);

        mockMvc.perform(post("/admin/venues/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/venues"));

        verify(venueService, times(1)).deleteVenue(1L);
    }
}
