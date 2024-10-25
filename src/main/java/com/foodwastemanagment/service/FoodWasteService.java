package com.foodwastemanagment.service;

import com.foodwastemanagment.Exceptions.APIException;
import com.foodwastemanagment.model.FoodWaste;
import com.foodwastemanagment.repository.FoodWasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodWasteService {
    @Autowired
    private FoodWasteRepository repository;

    public List<FoodWaste> getAllFoodWaste() {
        return repository.findAll();
    }

    public FoodWaste saveFoodWaste(FoodWaste foodWaste) {
        return repository.save(foodWaste);
    }

    public void deleteFoodWaste(Long id) {
        repository.deleteById(id);
    }
    @Autowired
    private FoodWasteRepository foodWasteRepository;

    public FoodWaste findFoodWasteById(Long id) {
        return foodWasteRepository.findById(id)
                .orElseThrow(() -> new APIException("Food waste record not found with ID: " + id));
    }
}