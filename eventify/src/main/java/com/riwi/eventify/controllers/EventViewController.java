package com.riwi.eventify.controllers;

import com.riwi.eventify.dto.EventSummaryDTO;
import com.riwi.eventify.models.Category;
import com.riwi.eventify.models.Event;
import com.riwi.eventify.models.Venue;
import com.riwi.eventify.services.CategoryService;
import com.riwi.eventify.services.EventService;
import com.riwi.eventify.services.VenueService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/events")
public class EventViewController {

    private final EventService eventService;
    private final VenueService venueService;
    private final CategoryService categoryService;

    public EventViewController(EventService eventService, VenueService venueService, CategoryService categoryService) {
        this.eventService = eventService;
        this.venueService = venueService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listEvents(
            Model model,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Slice<EventSummaryDTO> eventsPage = eventService.searchEventsForAdmin(city, category, pageable);

        model.addAttribute("eventsPage", eventsPage);
        model.addAttribute("events", eventsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("hasNext", eventsPage.hasNext());
        model.addAttribute("hasPrevious", eventsPage.hasPrevious());

        // Mantener filtros activos en el modelo para persistencia de contexto
        model.addAttribute("filterCity", city != null ? city : "");
        model.addAttribute("filterCategory", category != null ? category : "");

        return "events/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("event")) {
            model.addAttribute("event", new Event());
        }
        populateFormOptions(model);
        return "events/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return eventService.getEventById(id)
                .map(event -> {
                    model.addAttribute("event", event);
                    populateFormOptions(model);
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
            @RequestParam(value = "categoryIds", required = false) List<Long> categoryIds,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("event", event);
            populateFormOptions(model);
            model.addAttribute("error", "Revise los datos del formulario. La fecha debe tener formato valido.");
            return "events/form";
        }

        try {
            // Resolver categorías seleccionadas
            if (categoryIds != null && !categoryIds.isEmpty()) {
                Set<Category> categories = new HashSet<>();
                for (Long catId : categoryIds) {
                    Category cat = new Category();
                    cat.setId(catId);
                    categories.add(cat);
                }
                event.setCategories(categories);
            } else {
                event.setCategories(new HashSet<>());
            }

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
            populateFormOptions(model);
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

    private void populateFormOptions(Model model) {
        model.addAttribute("venues", venueService.getAllVenues());
        model.addAttribute("allCategories", categoryService.getAllCategories());
    }
}
