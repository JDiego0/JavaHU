package com.riwi.eventify.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"categories", "venue"})
@ToString(exclude = {"categories", "venue"})
@Entity
@Table(name = "events")
@SQLRestriction("active = true")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "venue_id", nullable = false)
    @JsonIgnoreProperties({"events"})
    private Venue venue;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "events_categories",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties({"events"})
    private Set<Category> categories = new HashSet<>();
    
    public Event(String name, String description, LocalDateTime date, Venue venue) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.venue = venue;
        this.active = true;
    }
    
    public void softDelete() {
        this.active = false;
    }
    
    public void deactivate() {
        this.active = false;
    }
}
