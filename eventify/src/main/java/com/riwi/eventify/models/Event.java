package com.riwi.eventify.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    @Column(nullable = false)
    private LocalDateTime date;
    
    @Column(nullable = false, length = 100)
    private String venue;
    
    public Event(String name, String description, LocalDateTime date, String venue) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.venue = venue;
    }
}
