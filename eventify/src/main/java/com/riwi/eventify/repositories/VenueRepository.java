package com.riwi.eventify.repositories;

import com.riwi.eventify.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    
    List<Venue> findByNameContainingIgnoreCase(String name);
    
    List<Venue> findByAddressContainingIgnoreCase(String address);
}
