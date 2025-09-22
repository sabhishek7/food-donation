package com.fooddonation.app.controller;

import com.fooddonation.app.entity.FoodImage;
import com.fooddonation.app.entity.FoodOffer;
import com.fooddonation.app.entity.User;
import com.fooddonation.app.service.FoodImageService;
import com.fooddonation.app.service.FoodOfferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/offers")
@PreAuthorize("hasRole('RECEIVER')")
public class ReceiverController {

    private final FoodOfferService foodOfferService;
    private final FoodImageService foodImageService;

    public ReceiverController(FoodOfferService foodOfferService, FoodImageService foodImageService) {
        this.foodOfferService = foodOfferService;
        this.foodImageService = foodImageService;
    }

    @GetMapping
    public String listActiveOffers(Pageable pageable, Model model) {
        Page<FoodOffer> offers = foodOfferService.getActiveOffers(pageable);
        model.addAttribute("offers", offers);
        return "receiver/offers";
    }

    @GetMapping("/data")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getOffersData() {
        List<FoodOffer> offers = foodOfferService.getAllActiveOffers();

        List<Map<String, Object>> transformedOffers = offers.stream()
                .map(offer -> {
                    Map<String, Object> offerData = new HashMap<>();
                    offerData.put("id", offer.getId());
                    offerData.put("title", offer.getTitle());
                    offerData.put("donorName", offer.getDonor().getName());
                    offerData.put("quantity", offer.getQuantity());
                    offerData.put("pickupLocation", offer.getPickupLocation());
                    offerData.put("availableFrom", offer.getAvailableFrom());
                    offerData.put("expiryAt", offer.getExpiryAt());
                    offerData.put("tags", offer.getTags());
                    offerData.put("description", offer.getDescription());

                    // Include image info
                    List<FoodImage> images = foodImageService.getImagesByOffer(offer);
                    offerData.put("hasImages", !images.isEmpty());
                    offerData.put("imageCount", images.size());
                    if (!images.isEmpty()) {
                        offerData.put("firstImage", "/images/" + offer.getId() + "/" + images.get(0).getFileName());
                    }

                    return offerData;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("rows", transformedOffers);
        response.put("total", transformedOffers.size());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/collect")
    public String markAsCollected(@PathVariable Long id, @AuthenticationPrincipal User receiver) {
        try {
            foodOfferService.markAsCollected(id, receiver);
            return "redirect:/offers/" + id + "?success=collected";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/offers/" + id + "?error=collection";
        }
    }

    @GetMapping("/{id}")
    public String viewOfferDetails(@PathVariable Long id, Model model) {
        FoodOffer offer = foodOfferService.getOfferById(id);
        List<FoodImage> images = foodImageService.getImagesByOffer(offer);

        model.addAttribute("offer", offer);
        model.addAttribute("images", images);
        return "receiver/offer-details";
    }
}
