package edu.ucaldas.back.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucaldas.back.DTO.ReviewDTO;
import edu.ucaldas.back.models.review.HouseReview;
import edu.ucaldas.back.models.review.HouseReviewData;
import edu.ucaldas.back.models.user.User;
import edu.ucaldas.back.repository.IHouseRepository;
import edu.ucaldas.back.repository.IHouseReviewRepository;
import edu.ucaldas.back.repository.IUserRepository;
import edu.ucaldas.back.service.validations.ValidationsHouse;
import edu.ucaldas.back.service.validations.ValidationsUser;
import jakarta.persistence.EntityNotFoundException;

@Service
public class HouseReviewService {
    @Autowired
    private IHouseReviewRepository houseReviewRepository;

    @Autowired
    private ValidationsHouse validationsHouse;

    @Autowired
    private ValidationsUser validationsUser;
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IHouseRepository houseRepository;

    public Page<ReviewDTO> getReviewsHouse(Long id, Pageable page) {
        if (!validationsHouse.existsHouse(id)) {
            throw new EntityNotFoundException("Invalid house ID");
        }
        Page<ReviewDTO> reviews = houseReviewRepository.findByHouseReviewedByHouseId(id, page)
                .map(houseReview -> new ReviewDTO(houseReview.getWriter().getEmail(), houseReview.getWriter().getName(),
                        houseReview.getComment(), houseReview.getStars(), houseReview.getDateTime()));
        return reviews;
    }

    public ReviewDTO createHouseReview(HouseReviewData houseReviewData) {
        // Implement the logic to create a new house review
        // This may involve saving the review to the database and returning the created review
        if (!validationsHouse.existsHouse(houseReviewData.houseId())) {
            throw new EntityNotFoundException("Invalid house ID");
        }
        if (!validationsUser.existsEmail(houseReviewData.writer())) {
            throw new EntityNotFoundException("Invalid user email");
        }   
        var writer = userRepository.getByEmailAndIsActiveTrue(houseReviewData.writer()).get();

        var houseReviewed = houseRepository.findByIdAndIsActiveTrue(houseReviewData.houseId()).get();



        HouseReview houseReview = new HouseReview(houseReviewData, writer, houseReviewed);
        houseReviewRepository.save(houseReview);
        return new ReviewDTO(houseReviewData.writer(), writer.getName(), houseReviewData.comment(), houseReviewData.stars(), houseReview.getDateTime()); // Placeholder return statement
    }

}
