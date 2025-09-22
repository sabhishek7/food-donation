package com.fooddonation.app.controller;

import com.fooddonation.app.entity.FoodOffer;
import com.fooddonation.app.entity.User;
import com.fooddonation.app.service.FoodOfferService;
import com.fooddonation.app.service.FoodImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/donor")
@PreAuthorize("hasRole('DONOR')")
public class DonorController {

    private final FoodOfferService foodOfferService;
    private final FoodImageService foodImageService; // Add this if you have it

    public DonorController(FoodOfferService foodOfferService, FoodImageService foodImageService) {
        this.foodOfferService = foodOfferService;
        this.foodImageService = foodImageService;
    }

    // List all offers for the donor
    @GetMapping("/offers")
    public String listOffers(@AuthenticationPrincipal User donor, Pageable pageable, Model model) {
        try {
            Page<FoodOffer> offers = foodOfferService.getDonorOffers(donor, pageable);
            model.addAttribute("offers", offers);
            return "donor/offers";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Unable to load offers");
            model.addAttribute("offers", Page.empty());
            return "donor/offers";
        }
    }

    // View specific offer details
    @GetMapping("/offers/{id}")
    public String viewOffer(@PathVariable Long id, @AuthenticationPrincipal User donor, Model model) {
        try {
            FoodOffer offer = foodOfferService.getOfferById(id);

            // Security check: ensure the offer belongs to the current donor
            if (!offer.getDonor().getId().equals(donor.getId())) {
                return "redirect:/donor/offers?error=unauthorized";
            }

            model.addAttribute("offer", offer);

            // Add images if you have FoodImageService
            if (foodImageService != null) {
                model.addAttribute("images", foodImageService.getImagesByOffer(offer));
            }

            return "donor/offer-details";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/donor/offers?error=notfound";
        }
    }

    // Show form for creating new offer
    @GetMapping("/offers/new")
    public String newOfferForm(Model model) {
        model.addAttribute("offer", new FoodOffer());
        return "donor/new-offer";
    }

    // Create new offer
    @PostMapping("/offers")
    public String createOffer(@AuthenticationPrincipal User donor,
                              @ModelAttribute FoodOffer offer,
                              @RequestParam(value = "images", required = false) List<MultipartFile> images) {
        try {
            foodOfferService.createOffer(offer, images, donor);
            return "redirect:/donor/offers?success=created";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/donor/offers/new?error=creation";
        }
    }

    // Show edit form
    @GetMapping("/offers/{id}/edit")
    public String editOfferForm(@PathVariable Long id, @AuthenticationPrincipal User donor, Model model) {
        try {
            FoodOffer offer = foodOfferService.getOfferById(id);

            // Security check: ensure the offer belongs to the current donor
            if (!offer.getDonor().getId().equals(donor.getId())) {
                return "redirect:/donor/offers?error=unauthorized";
            }

            model.addAttribute("offer", offer);

            // Add images if you have FoodImageService
            if (foodImageService != null) {
                model.addAttribute("images", foodImageService.getImagesByOffer(offer));
            }

            return "donor/edit-offer";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/donor/offers?error=notfound";
        }
    }

    // Update offer
    @PostMapping("/offers/{id}")
    public String updateOffer(@PathVariable Long id,
                              @AuthenticationPrincipal User donor,
                              @ModelAttribute FoodOffer offer,
                              @RequestParam(value = "images", required = false) List<MultipartFile> images) {
        try {
            foodOfferService.updateOffer(id, offer, images, donor);
            return "redirect:/donor/offers?success=updated";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/donor/offers/{id}/edit?error=update";
        }
    }

    // Delete offer
    @PostMapping("/offers/{id}/delete")
    public String deleteOffer(@PathVariable Long id, @AuthenticationPrincipal User donor) {
        try {
            foodOfferService.deleteOffer(id, donor);
            return "redirect:/donor/offers?success=deleted";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/donor/offers?error=delete";
        }
    }
    @PostMapping("/offers/{id}/uncollect")
    public String markAsUncollected(@PathVariable Long id, @AuthenticationPrincipal User donor) {
        try {
            foodOfferService.markAsUncollected(id, donor);
            return "redirect:/donor/offers/" + id + "?success=uncollected";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/donor/offers/" + id + "?error=uncollection";
        }
    }
}
