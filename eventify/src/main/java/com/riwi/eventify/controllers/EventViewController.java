package com.riwi.eventify.controllers;

import com.riwi.eventify.models.Event;
import com.riwi.eventify.models.Venue;
import com.riwi.eventify.services.EventService;
import com.riwi.eventify.services.VenueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/events")
public class EventViewController {

    private final EventService eventService;
    private final VenueService venueService;

    public EventViewController(EventService eventService, VenueService venueService) {
        this.eventService = eventService;
        this.venueService = venueService;
    }

    @ModelAttribute("venues")
    public List<Venue> populateVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping
    public String listEvents(Model model, @RequestParam(required = false) String success) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        if (success != null) {
            model.addAttribute("successMessage", success);
        }
        return "events/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("event")) {
            model.addAttribute("event", new Event());
        }
        return "events/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return eventService.getEventById(id)
                .map(event -> {
                    model.addAttribute("event", event);
                    return "events/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Evento no encontrado");
                    return "redirect:/admin/events";
                });
    }

    @PostMapping("/save")
    public String saveEvent(
            @ModelAttribute Event event,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("event", event);
            model.addAttribute("error", "Revise los datos del formulario. La fecha debe tener formato valido.");
            return "events/form";
        }

        try {
            if (event.getId() != null) {
                eventService.updateEvent(event.getId(), event);
                redirectAttributes.addFlashAttribute("success", "Evento actualizado exitosamente");
            } else {
                eventService.createEvent(event);
                redirectAttributes.addFlashAttribute("success", "Evento creado exitosamente");
            }
            return "redirect:/admin/events";
        } catch (IllegalArgumentException e) {
            model.addAttribute("event", event);
            model.addAttribute("error", "Datos invalidos: " + e.getMessage());
            return "events/form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el evento: " + e.getMessage());
            return "redirect:/admin/events";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            eventService.deleteEvent(id);
            redirectAttributes.addFlashAttribute("success", "Evento eliminado exitosamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el evento: " + e.getMessage());
        }
        return "redirect:/admin/events";
    }
}
