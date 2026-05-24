package com.riwi.eventify.controllers;

import com.riwi.eventify.models.Venue;
import com.riwi.eventify.services.VenueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/venues")
public class VenueViewController {

    private final VenueService venueService;

    public VenueViewController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public String listVenues(Model model, @RequestParam(required = false) String success) {
        List<Venue> venues = venueService.getAllVenues();
        model.addAttribute("venues", venues);
        if (success != null) {
            model.addAttribute("successMessage", success);
        }
        return "venues/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("venue", new Venue());
        return "venues/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return venueService.getVenueById(id)
                .map(venue -> {
                    model.addAttribute("venue", venue);
                    return "venues/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Lugar no encontrado");
                    return "redirect:/admin/venues";
                });
    }

    @PostMapping("/save")
    public String saveVenue(@ModelAttribute Venue venue, RedirectAttributes redirectAttributes) {
        try {
            if (venue.getId() != null) {
                venueService.updateVenue(venue.getId(), venue);
                redirectAttributes.addFlashAttribute("success", "Lugar actualizado exitosamente");
            } else {
                venueService.createVenue(venue);
                redirectAttributes.addFlashAttribute("success", "Lugar creado exitosamente");
            }
            return "redirect:/admin/venues";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Datos inválidos: " + e.getMessage());
            return "redirect:/admin/venues/new";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el lugar: " + e.getMessage());
            return "redirect:/admin/venues";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteVenue(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            venueService.deleteVenue(id);
            redirectAttributes.addFlashAttribute("success", "Lugar eliminado exitosamente");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el lugar: " + e.getMessage());
        }
        return "redirect:/admin/venues";
    }
}
