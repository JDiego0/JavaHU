package com.riwi.eventify.repositories;

import com.riwi.eventify.dto.EventSummaryDTO;
import com.riwi.eventify.models.Event;
import com.riwi.eventify.models.Venue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByNameContainingIgnoreCaseOrderByDateDesc(String name);

    List<Event> findByVenueOrderByDateDesc(Venue venue);

    @Query("SELECT new com.riwi.eventify.dto.EventSummaryDTO(" +
           "e.id, e.name, e.date, v.name, v.city) " +
           "FROM Event e JOIN e.venue v " +
           "WHERE LOWER(v.city) LIKE LOWER(CONCAT('%', :city, '%')) " +
           "ORDER BY e.date DESC")
    Slice<EventSummaryDTO> findByCity(@Param("city") String city, Pageable pageable);

    @Query("SELECT DISTINCT new com.riwi.eventify.dto.EventSummaryDTO(" +
           "e.id, e.name, e.date, v.name, v.city) " +
           "FROM Event e JOIN e.venue v JOIN e.categories c " +
           "WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :categoryName, '%')) " +
           "ORDER BY e.date DESC")
    Slice<EventSummaryDTO> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);

    @Query("SELECT new com.riwi.eventify.dto.EventSummaryDTO(" +
           "e.id, e.name, e.date, v.name, v.city) " +
           "FROM Event e JOIN e.venue v " +
           "WHERE v.capacity >= :minCapacity " +
           "ORDER BY e.date DESC")
    Slice<EventSummaryDTO> findByMinCapacity(@Param("minCapacity") Integer minCapacity, Pageable pageable);

    @Query("SELECT new com.riwi.eventify.dto.EventSummaryDTO(" +
           "e.id, e.name, e.date, v.name, v.city) " +
           "FROM Event e JOIN e.venue v " +
           "WHERE e.date BETWEEN :startDate AND :endDate " +
           "ORDER BY e.date DESC")
    Slice<EventSummaryDTO> findByDateBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("SELECT DISTINCT new com.riwi.eventify.dto.EventSummaryDTO(" +
           "e.id, e.name, e.date, v.name, v.city) " +
           "FROM Event e JOIN e.venue v LEFT JOIN e.categories c " +
           "WHERE (:city IS NULL OR LOWER(v.city) LIKE LOWER(CONCAT('%', :city, '%'))) " +
           "AND (:categoryName IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :categoryName, '%'))) " +
           "AND (:minCapacity IS NULL OR v.capacity >= :minCapacity) " +
           "AND (:startDate IS NULL OR e.date >= :startDate) " +
           "AND (:endDate IS NULL OR e.date <= :endDate) " +
           "ORDER BY e.date DESC")
    Slice<EventSummaryDTO> searchEvents(
            @Param("city") String city,
            @Param("categoryName") String categoryName,
            @Param("minCapacity") Integer minCapacity,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("SELECT new com.riwi.eventify.dto.EventSummaryDTO(" +
           "e.id, e.name, e.date, v.name, v.city) " +
           "FROM Event e JOIN e.venue v " +
           "ORDER BY e.date DESC")
    Slice<EventSummaryDTO> findAllEventSummaries(Pageable pageable);

    @Query("SELECT DISTINCT e FROM Event e " +
           "JOIN FETCH e.venue " +
           "LEFT JOIN FETCH e.categories " +
           "ORDER BY e.date DESC")
    List<Event> findAllWithVenueAndCategories();

    @Query("SELECT DISTINCT e FROM Event e " +
           "JOIN FETCH e.venue " +
           "LEFT JOIN FETCH e.categories " +
           "WHERE e.id = :id")
    Optional<Event> findByIdWithRelations(@Param("id") Long id);

    @Query("SELECT DISTINCT new com.riwi.eventify.dto.EventSummaryDTO(" +
           "e.id, e.name, e.date, v.name, v.city) " +
           "FROM Event e JOIN e.venue v LEFT JOIN e.categories c " +
           "WHERE (:city IS NULL OR LOWER(v.city) LIKE LOWER(CONCAT('%', :city, '%'))) " +
           "AND (:category IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :category, '%'))) " +
           "ORDER BY e.date DESC")
    Slice<EventSummaryDTO> searchEventSummariesForAdmin(
            @Param("city") String city,
            @Param("category") String category,
            Pageable pageable);
}
