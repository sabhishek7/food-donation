package com.fooddonation.app.service;

import com.fooddonation.app.entity.FoodOffer;
import com.fooddonation.app.entity.FoodImage;
import com.fooddonation.app.entity.Status;
import com.fooddonation.app.entity.User;
import com.fooddonation.app.repository.FoodImageRepository;
import com.fooddonation.app.repository.FoodOfferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FoodOfferService {

    private final FoodOfferRepository foodOfferRepository;
    private final FoodImageRepository foodImageRepository;
    private final String uploadDir = "/uploads/food/";

    public FoodOfferService(FoodOfferRepository foodOfferRepository, FoodImageRepository foodImageRepository) {
        this.foodOfferRepository = foodOfferRepository;
        this.foodImageRepository = foodImageRepository;
    }

    public Page<FoodOffer> getDonorOffers(User donor, Pageable pageable) {
        return foodOfferRepository.findByDonor(donor, pageable);
    }

    public Page<FoodOffer> getActiveOffers(Pageable pageable) {
        return foodOfferRepository.findByStatus(Status.ACTIVE, pageable);
    }


    public FoodOffer getOfferById(Long id) {
        return foodOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found with id: " + id));
    }

    public void createOffer(FoodOffer offer, List<MultipartFile> images, User donor) {
        offer.setDonor(donor);
        offer.setStatus(Status.ACTIVE);
        FoodOffer savedOffer = foodOfferRepository.save(offer);
        saveImages(savedOffer, images);
    }

    public void updateOffer(Long id, FoodOffer updatedOffer, List<MultipartFile> images, User donor) {
        FoodOffer existingOffer = getOfferById(id);

        // Security check: ensure the offer belongs to the current donor
        if (!existingOffer.getDonor().getId().equals(donor.getId())) {
            throw new RuntimeException("Unauthorized to update this offer");
        }

        // Update fields
        existingOffer.setTitle(updatedOffer.getTitle());
        existingOffer.setDescription(updatedOffer.getDescription());
        existingOffer.setQuantity(updatedOffer.getQuantity());
        existingOffer.setPickupLocation(updatedOffer.getPickupLocation());
        existingOffer.setLatitude(updatedOffer.getLatitude());
        existingOffer.setLongitude(updatedOffer.getLongitude());
        existingOffer.setAvailableFrom(updatedOffer.getAvailableFrom());
        existingOffer.setExpiryAt(updatedOffer.getExpiryAt());
        existingOffer.setTags(updatedOffer.getTags());

        FoodOffer savedOffer = foodOfferRepository.save(existingOffer);

        // Add new images if provided
        if (images != null && !images.isEmpty()) {
            saveImages(savedOffer, images);
        }
    }

    public void markAsCollected(Long offerId, User receiver) {
        FoodOffer offer = getOfferById(offerId);

        // Check if offer is still active
        if (offer.getStatus() != Status.ACTIVE) {
            throw new RuntimeException("This offer is no longer available");
        }

        // Mark as collected
        offer.setStatus(Status.COLLECTED);
        offer.setCollectedBy(receiver);
        offer.setCollectedAt(LocalDateTime.now());

        foodOfferRepository.save(offer);
    }

    public void markAsUncollected(Long offerId, User donor) {
        FoodOffer offer = getOfferById(offerId);

        // Security check: only donor can uncollect
        if (!offer.getDonor().getId().equals(donor.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        // Mark as active again
        offer.setStatus(Status.ACTIVE);
        offer.setCollectedBy(null);
        offer.setCollectedAt(null);

        foodOfferRepository.save(offer);
    }


    public void deleteOffer(Long id, User donor) {
        FoodOffer offer = getOfferById(id);

        // Security check: ensure the offer belongs to the current donor
        if (!offer.getDonor().getId().equals(donor.getId())) {
            throw new RuntimeException("Unauthorized to delete this offer");
        }

        // Soft delete by changing status
        offer.setStatus(Status.DELETED);
        foodOfferRepository.save(offer);
    }
    public List<FoodOffer> getAllActiveOffers() {
        return foodOfferRepository.findByStatusOrderByCreatedAtDesc(Status.ACTIVE);
    }

    private void saveImages(FoodOffer offer, List<MultipartFile> images) {
        if (images == null || images.isEmpty()) return;

        String offerDir = uploadDir + offer.getId() + "/";
        new File(offerDir).mkdirs();

        for (MultipartFile image : images) {
            if (image.isEmpty()) continue;

            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path path = Paths.get(offerDir + fileName);

            try {
                Files.write(path, image.getBytes());

                FoodImage foodImage = new FoodImage();
                foodImage.setFoodOffer(offer);
                foodImage.setFilePath(path.toString());
                foodImage.setFileName(fileName);
                foodImage.setContentType(image.getContentType());
                foodImage.setSize(image.getSize());
                foodImageRepository.save(foodImage);

            } catch (IOException e) {
                throw new RuntimeException("Failed to store image: " + fileName, e);
            }
        }
    }




}
