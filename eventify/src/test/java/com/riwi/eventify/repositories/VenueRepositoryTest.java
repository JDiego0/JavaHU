package com.riwi.eventify.repositories;

import com.riwi.eventify.models.Venue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VenueRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VenueRepository venueRepository;

    private Venue venue1;
    private Venue venue2;
    private Venue venue3;

    @BeforeEach
    void setUp() {
        venue1 = new Venue("Centro de Convenciones", "Av. Principal 123", 5000, "Lima");
        venue2 = new Venue("Estadio Nacional", "Calle Deportiva 456", 50000, "Lima");
        venue3 = new Venue("Sala de Conferencias", "Plaza Central 789", 200, "Callao");
    }

    @Test
    void whenSaveVenue_thenVenueIsPersisted() {
        Venue savedVenue = venueRepository.save(venue1);
        
        assertThat(savedVenue).isNotNull();
        assertThat(savedVenue.getId()).isNotNull();
        assertThat(savedVenue.getName()).isEqualTo(venue1.getName());
    }

    @Test
    void whenFindById_thenReturnVenue() {
        Venue savedVenue = entityManager.persist(venue1);
        
        Venue foundVenue = venueRepository.findById(savedVenue.getId()).orElse(null);
        
        assertThat(foundVenue).isNotNull();
        assertThat(foundVenue.getName()).isEqualTo(venue1.getName());
    }

    @Test
    void whenFindAll_thenReturnAllVenues() {
        entityManager.persist(venue1);
        entityManager.persist(venue2);
        entityManager.persist(venue3);
        
        List<Venue> venues = venueRepository.findAll();
        
        assertThat(venues).hasSize(3);
        assertThat(venues).extracting(Venue::getName).containsExactlyInAnyOrder(
            venue1.getName(), venue2.getName(), venue3.getName()
        );
    }

    @Test
    void whenFindAllWithPagination_thenReturnPage() {
        entityManager.persist(venue1);
        entityManager.persist(venue2);
        entityManager.persist(venue3);
        
        Pageable pageable = PageRequest.of(0, 2, Sort.by("name").ascending());
        Page<Venue> venuePage = venueRepository.findAll(pageable);
        
        assertThat(venuePage.getContent()).hasSize(2);
        assertThat(venuePage.getTotalElements()).isEqualTo(3);
        assertThat(venuePage.getTotalPages()).isEqualTo(2);
    }

    @Test
    void whenFindByNameContainingIgnoreCase_thenReturnMatchingVenues() {
        entityManager.persist(venue1);
        entityManager.persist(venue2);
        entityManager.persist(venue3);
        
        List<Venue> venues = venueRepository.findByNameContainingIgnoreCase("centro");
        
        assertThat(venues).hasSize(1);
        assertThat(venues.get(0).getName()).isEqualTo(venue1.getName());
    }

    @Test
    void whenFindByAddressContainingIgnoreCase_thenReturnMatchingVenues() {
        entityManager.persist(venue1);
        entityManager.persist(venue2);
        entityManager.persist(venue3);
        
        List<Venue> venues = venueRepository.findByAddressContainingIgnoreCase("av.");
        
        assertThat(venues).hasSize(1);
        assertThat(venues.get(0).getName()).isEqualTo(venue1.getName());
    }

    @Test
    void whenDeleteById_thenVenueIsRemoved() {
        Venue savedVenue = entityManager.persist(venue1);
        
        venueRepository.deleteById(savedVenue.getId());
        
        Venue deletedVenue = venueRepository.findById(savedVenue.getId()).orElse(null);
        assertThat(deletedVenue).isNull();
    }

    @Test
    void whenExistsById_thenReturnTrue() {
        Venue savedVenue = entityManager.persist(venue1);
        
        boolean exists = venueRepository.existsById(savedVenue.getId());
        
        assertThat(exists).isTrue();
    }

    @Test
    void whenExistsByIdWithNonExistentId_thenReturnFalse() {
        boolean exists = venueRepository.existsById(9999L);
        
        assertThat(exists).isFalse();
    }
}
