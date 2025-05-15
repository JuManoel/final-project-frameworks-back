package edu.ucaldas.back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.ucaldas.back.models.review.HouseReview;

@Repository
public interface IHouseReviewRepository extends IReviewRepository{
    // Custom query methods can be defined here if needed

    @Query("SELECT hr FROM HouseReview hr WHERE hr.houseReviewed.id = :houseId AND hr.isActive = true ORDER BY hr.dateTime DESC")
    Page<HouseReview> findByHouseReviewedByHouseId(@Param("houseId")Long id, Pageable page);

    @Query("SELECT AVG(hr.stars) FROM HouseReview hr WHERE hr.houseReviewed.id = :houseId AND hr.isActive = true")
    Float findAverageStarsByHouseId(@Param("houseId") Long id);

}
