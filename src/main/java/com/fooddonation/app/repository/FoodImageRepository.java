package com.fooddonation.app.repository;

import com.fooddonation.app.entity.FoodImage;
import com.fooddonation.app.entity.FoodOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodImageRepository extends JpaRepository<FoodImage, Long> {
    List<FoodImage> findByFoodOffer(FoodOffer foodOffer);
    List<FoodImage> findByFoodOfferOrderByDisplayOrder(FoodOffer foodOffer);

}
