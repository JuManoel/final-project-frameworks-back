package edu.ucaldas.back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.ucaldas.back.models.review.UserReview;

@Repository
public interface IUserReviewRepository extends IReviewRepository {

    @Query("SELECT ur FROM UserReview ur WHERE ur.userReviewed.email = :email AND ur.isActive = true")
    Page<UserReview> findByUserReviewedEmail(@Param("email") String email, org.springdoc.core.converters.models.Pageable page);
    
}
