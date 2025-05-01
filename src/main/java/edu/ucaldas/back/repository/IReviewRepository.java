package edu.ucaldas.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import edu.ucaldas.back.models.review.Review;

@NoRepositoryBean
public interface IReviewRepository extends JpaRepository<Review, Long>{

}
