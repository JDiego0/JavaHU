package com.riwi.eventify.repositories;

import com.riwi.eventify.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    List<Event> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT e FROM Event e WHERE e.venue = :venue")
    List<Event> findByVenue(@Param("venue") String venue);
}
