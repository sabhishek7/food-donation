package com.fooddonation.app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "food_offer")
@Data
public class FoodOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private User donor;

    private String title;
    private String description;
    private String quantity;
    private String pickupLocation;
    private Double latitude;
    private Double longitude;
    private LocalDateTime availableFrom;
    private LocalDateTime expiryAt;
    private String tags;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)  // Specify column length
    private Status status = Status.ACTIVE;

    // New fields for collection tracking
    @ManyToOne
    @JoinColumn(name = "collected_by_id")
    private User collectedBy;

    @Column(name = "collected_at")
    private LocalDateTime collectedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
