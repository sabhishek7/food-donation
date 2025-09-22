package com.fooddonation.app.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "food_image")
@Data
public class FoodImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_offer_id")
    private FoodOffer foodOffer;

    private String filePath;
    private String fileName;
    private String contentType;
    private Long size;
    private Integer displayOrder;

    private LocalDateTime createdAt = LocalDateTime.now();
}
