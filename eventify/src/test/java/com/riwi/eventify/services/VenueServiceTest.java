package com.riwi.eventify.services;

import com.riwi.eventify.models.Venue;
import com.riwi.eventify.repositories.VenueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VenueServiceTest {

    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private VenueService venueService;

    private Venue testVenue;

    @BeforeEach
    void setUp() {
        testVenue = new Venue("Estadio Nacional", "Av. Principal 123", 50000);
    }

    @Test
    void createVenue_ValidVenue_ShouldReturnSavedVenue() {
        // Given
        Venue savedVenue = new Venue(1L, "Estadio Nacional", "Av. Principal 123", 50000);
        when(venueRepository.save(any(Venue.class))).thenReturn(savedVenue);

        // When
        Venue result = venueService.createVenue(testVenue);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Estadio Nacional", result.getName());
        assertEquals(50000, result.getCapacity());
        verify(venueRepository, times(1)).save(testVenue);
    }

    @Test
    void createVenue_EmptyName_ShouldThrowIllegalArgumentException() {
        // Given
        testVenue.setName("");

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> venueService.createVenue(testVenue)
        );
        assertEquals("El nombre del lugar no puede estar vacío", exception.getMessage());
        verify(venueRepository, never()).save(any());
    }

    @Test
    void createVenue_NullName_ShouldThrowIllegalArgumentException() {
        // Given
        testVenue.setName(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> venueService.createVenue(testVenue)
        );
        assertEquals("El nombre del lugar no puede estar vacío", exception.getMessage());
        verify(venueRepository, never()).save(any());
    }

    @Test
    void createVenue_EmptyAddress_ShouldThrowIllegalArgumentException() {
        // Given
        testVenue.setAddress("");

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> venueService.createVenue(testVenue)
        );
        assertEquals("La dirección del lugar es obligatoria", exception.getMessage());
        verify(venueRepository, never()).save(any());
    }

    @Test
    void createVenue_NullAddress_ShouldThrowIllegalArgumentException() {
        // Given
        testVenue.setAddress(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> venueService.createVenue(testVenue)
        );
        assertEquals("La dirección del lugar es obligatoria", exception.getMessage());
        verify(venueRepository, never()).save(any());
    }

    @Test
    void createVenue_NullCapacity_ShouldThrowIllegalArgumentException() {
        // Given
        testVenue.setCapacity(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> venueService.createVenue(testVenue)
        );
        assertEquals("La capacidad del lugar debe ser mayor a cero", exception.getMessage());
        verify(venueRepository, never()).save(any());
    }

    @Test
    void createVenue_ZeroCapacity_ShouldThrowIllegalArgumentException() {
        // Given
        testVenue.setCapacity(0);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> venueService.createVenue(testVenue)
        );
        assertEquals("La capacidad del lugar debe ser mayor a cero", exception.getMessage());
        verify(venueRepository, never()).save(any());
    }

    @Test
    void createVenue_NegativeCapacity_ShouldThrowIllegalArgumentException() {
        // Given
        testVenue.setCapacity(-100);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> venueService.createVenue(testVenue)
        );
        assertEquals("La capacidad del lugar debe ser mayor a cero", exception.getMessage());
        verify(venueRepository, never()).save(any());
    }

    @Test
    void getAllVenues_ShouldReturnAllVenues() {
        // Given
        List<Venue> venues = Arrays.asList(
                new Venue(1L, "Estadio Nacional", "Av. Principal 123", 50000),
                new Venue(2L, "Teatro Municipal", "Calle Cultura 456", 800)
        );
        when(venueRepository.findAll()).thenReturn(venues);

        // When
        List<Venue> result = venueService.getAllVenues();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Estadio Nacional", result.get(0).getName());
        verify(venueRepository, times(1)).findAll();
    }

    @Test
    void getVenueById_ExistingId_ShouldReturnVenue() {
        // Given
        Venue venue = new Venue(1L, "Estadio Nacional", "Av. Principal 123", 50000);
        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));

        // When
        Optional<Venue> result = venueService.getVenueById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Estadio Nacional", result.get().getName());
        verify(venueRepository, times(1)).findById(1L);
    }

    @Test
    void getVenueById_NonExistingId_ShouldReturnEmpty() {
        // Given
        when(venueRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Venue> result = venueService.getVenueById(999L);

        // Then
        assertFalse(result.isPresent());
        verify(venueRepository, times(1)).findById(999L);
    }

    @Test
    void deleteVenue_ValidId_ShouldCallRepository() {
        // When
        venueService.deleteVenue(1L);

        // Then
        verify(venueRepository, times(1)).deleteById(1L);
    }
}
