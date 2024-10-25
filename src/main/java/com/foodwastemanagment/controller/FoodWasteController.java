package com.foodwastemanagment.controller;

import com.foodwastemanagment.model.FoodWaste;
import com.foodwastemanagment.repository.FoodWasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/foodwaste")
public class FoodWasteController {
    @Autowired
    private FoodWasteRepository foodWasteRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping
    public String listFoodWaste(Model model) {
        model.addAttribute("foodWastes", foodWasteRepository.findAll());
        return "list-food-waste";
    }

    @GetMapping("/add")
    public String addFoodWasteForm(Model model) {
        model.addAttribute("foodWaste", new FoodWaste());
        return "add-food-waste";
    }

    @PostMapping("/add")
    public String addFoodWaste(@ModelAttribute FoodWaste foodWaste) {
        foodWasteRepository.save(foodWaste);
        return "redirect:/foodwaste";
    }

    @GetMapping("/edit/{id}")
    public String editFoodWasteForm(@PathVariable Long id, Model model) {
        FoodWaste foodWaste = foodWasteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid food waste Id:" + id));
        model.addAttribute("foodWaste", foodWaste);
        return "edit-food-waste";
    }

    @PostMapping("/edit")
    public String editFoodWaste(@ModelAttribute FoodWaste foodWaste) {
        foodWasteRepository.save(foodWaste);
        return "redirect:/foodwaste";
    }

    @GetMapping("/delete/{id}")
    public String deleteFoodWaste(@PathVariable Long id) {
        FoodWaste foodWaste = foodWasteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid food waste Id:" + id));
        foodWasteRepository.delete(foodWaste);
        return "redirect:/foodwaste";
    }

    @GetMapping("/list/donor")
    public String listFoodWasteForDonor(Model model) {
        model.addAttribute("foodWastes", foodWasteRepository.findAll());
        return "list-food-waste";
    }

    @GetMapping("/list/receiver")
    public String listFoodWasteForReceiver(Model model) {
        model.addAttribute("foodWastes", foodWasteRepository.findAll());
        return "list-food-waste-receiver";
    }
}
