package com.riwi.eventify.config;

import com.riwi.eventify.models.Event;
import com.riwi.eventify.models.Venue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SeederConfig {

    @Bean
    @ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = false)
    public List<Venue> venueSeeder() {
        List<Venue> venues = new ArrayList<>();
        
        venues.add(new Venue(1L, "Estadio Nacional", "Av. Principal 123", 50000));
        venues.add(new Venue(2L, "Teatro Municipal", "Calle Cultura 456", 800));
        venues.add(new Venue(3L, "Centro de Convenciones", "Zona Comercial 789", 2000));
        
        return venues;
    }

    @Bean
    @ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = false)
    public List<Event> eventSeeder() {
        List<Event> events = new ArrayList<>();
        
        events.add(new Event(1L, "Concierto de Rock", "Gran concierto con las mejores bandas", 
                           LocalDateTime.now().plusDays(30), "Estadio Nacional"));
        events.add(new Event(2L, "Obra de Teatro", "Clásico del teatro universal", 
                           LocalDateTime.now().plusDays(15), "Teatro Municipal"));
        events.add(new Event(3L, "Conferencia Tecnológica", "Las últimas tendencias en tecnología", 
                           LocalDateTime.now().plusDays(45), "Centro de Convenciones"));
        
        return events;
    }
}
