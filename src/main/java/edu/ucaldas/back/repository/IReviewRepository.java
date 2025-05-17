package edu.ucaldas.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import edu.ucaldas.back.models.review.Review;

/**
 * Repository interface for managing {@link Review} entities.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations and query methods
 * for the {@code Review} entity with a primary key of type {@code Long}.
 * </p>
 * <p>
 * Marked with {@link NoRepositoryBean} to indicate that this interface should not be instantiated
 * directly as a Spring Data repository bean.
 * </p>
 */
@NoRepositoryBean
public interface IReviewRepository extends JpaRepository<Review, Long>{

}
