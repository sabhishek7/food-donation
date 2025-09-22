package com.fooddonation.app.service;



import com.fooddonation.app.entity.FoodImage;
import com.fooddonation.app.entity.FoodOffer;
import com.fooddonation.app.repository.FoodImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodImageService {

    private final FoodImageRepository foodImageRepository;

    public FoodImageService(FoodImageRepository foodImageRepository) {
        this.foodImageRepository = foodImageRepository;
    }

    public List<FoodImage> getImagesByOffer(FoodOffer offer) {
        return foodImageRepository.findByFoodOfferOrderByDisplayOrder(offer);
    }

    public void deleteImage(Long imageId) {
        foodImageRepository.deleteById(imageId);
    }
}
