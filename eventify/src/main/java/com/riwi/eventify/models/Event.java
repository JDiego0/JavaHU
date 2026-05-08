package com.riwi.eventify.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime date;
    private String venue;
    
    public Event(String name, String description, LocalDateTime date, String venue) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.venue = venue;
    }
}
