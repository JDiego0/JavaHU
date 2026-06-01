package com.riwi.eventify.config;

import com.riwi.eventify.models.Category;
import com.riwi.eventify.models.Event;
import com.riwi.eventify.models.Venue;
import com.riwi.eventify.repositories.CategoryRepository;
import com.riwi.eventify.repositories.EventRepository;
import com.riwi.eventify.repositories.VenueRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
public class SeederConfig {

    @Bean
    @ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = false)
    public CommandLineRunner dataSeeder(VenueRepository venueRepository,
                                         CategoryRepository categoryRepository,
                                         EventRepository eventRepository) {
        return args -> {
            // Solo ejecutar si no hay datos
            if (venueRepository.count() > 0) {
                return;
            }

            // ==================== VENUES ====================
            Venue estadio = venueRepository.save(new Venue("Estadio Nacional", "Av. Principal 123", 50000, "Lima"));
            Venue teatro = venueRepository.save(new Venue("Teatro Municipal", "Calle Cultura 456", 800, "Lima"));
            Venue centro = venueRepository.save(new Venue("Centro de Convenciones", "Zona Comercial 789", 2000, "Lima"));
            Venue arena = venueRepository.save(new Venue("Arena del Pacífico", "Blvd. Costero 321", 15000, "Callao"));
            Venue jardin = venueRepository.save(new Venue("Jardín Botánico", "Parque Central 100", 500, "Miraflores"));

            // ==================== CATEGORIES ====================
            Category conciertos = categoryRepository.save(new Category("Conciertos", "Eventos musicales en vivo con artistas nacionales e internacionales"));
            Category talleres = categoryRepository.save(new Category("Talleres", "Sesiones prácticas de aprendizaje y desarrollo de habilidades"));
            Category conferencias = categoryRepository.save(new Category("Conferencias", "Charlas y presentaciones sobre temas especializados"));
            Category deportes = categoryRepository.save(new Category("Deportes", "Competencias y eventos deportivos de alto rendimiento"));
            Category gastronomia = categoryRepository.save(new Category("Gastronomía", "Ferias, degustaciones y experiencias culinarias"));
            Category festivales = categoryRepository.save(new Category("Festivales", "Celebraciones culturales con múltiples actividades"));
            Category teatroCategoria = categoryRepository.save(new Category("Teatro", "Obras teatrales, musicales y artes escénicas"));

            // ==================== EVENTS ====================
            Event conciertoRock = new Event("Concierto de Rock", "Gran concierto con las mejores bandas nacionales e internacionales",
                    LocalDateTime.now().plusDays(30), estadio);
            conciertoRock.setCategories(new HashSet<>(Arrays.asList(conciertos, festivales)));
            eventRepository.save(conciertoRock);

            Event obraTeatro = new Event("Obra de Teatro: Hamlet", "Clásico del teatro universal interpretado por elenco premiado",
                    LocalDateTime.now().plusDays(15), teatro);
            obraTeatro.setCategories(new HashSet<>(Arrays.asList(teatroCategoria)));
            eventRepository.save(obraTeatro);

            Event confTech = new Event("Conferencia Tecnológica 2026", "Las últimas tendencias en IA, Cloud y desarrollo de software",
                    LocalDateTime.now().plusDays(45), centro);
            confTech.setCategories(new HashSet<>(Arrays.asList(conferencias, talleres)));
            eventRepository.save(confTech);

            Event feriaGastro = new Event("Feria Gastronómica", "Degustación de platos típicos de todas las regiones",
                    LocalDateTime.now().plusDays(20), jardin);
            feriaGastro.setCategories(new HashSet<>(Arrays.asList(gastronomia, festivales)));
            eventRepository.save(feriaGastro);

            Event maratonUrbana = new Event("Maratón Urbana 10K", "Carrera de 10 kilómetros por las principales avenidas de la ciudad",
                    LocalDateTime.now().plusDays(60), arena);
            maratonUrbana.setCategories(new HashSet<>(Arrays.asList(deportes)));
            eventRepository.save(maratonUrbana);
        };
    }
}
