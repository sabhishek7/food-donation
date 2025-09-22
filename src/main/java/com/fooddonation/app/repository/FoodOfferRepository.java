package com.fooddonation.app.repository;

import com.fooddonation.app.entity.FoodOffer;
import com.fooddonation.app.entity.Status;
import com.fooddonation.app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodOfferRepository extends JpaRepository<FoodOffer, Long> {
    Page<FoodOffer> findByDonor(User donor, Pageable pageable);
    Page<FoodOffer> findByStatus(Status status, Pageable pageable);
    List<FoodOffer> findByStatusOrderByCreatedAtDesc(Status status);
}
