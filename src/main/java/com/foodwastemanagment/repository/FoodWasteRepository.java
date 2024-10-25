package com.foodwastemanagment.repository;

import com.foodwastemanagment.model.FoodWaste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodWasteRepository extends JpaRepository<FoodWaste, Long> {
}