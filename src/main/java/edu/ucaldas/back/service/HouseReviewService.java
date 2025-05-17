package edu.ucaldas.back.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucaldas.back.DTO.ReviewDTO;
import edu.ucaldas.back.models.review.HouseReview;
import edu.ucaldas.back.models.review.HouseReviewData;
import edu.ucaldas.back.models.user.User;
import edu.ucaldas.back.repository.IHouseRepository;
import edu.ucaldas.back.repository.IHouseReviewRepository;
import edu.ucaldas.back.service.validations.ValidationsHouse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class HouseReviewService {
    @Autowired
    private IHouseReviewRepository houseReviewRepository;

    @Autowired
    private ValidationsHouse validationsHouse;

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

    @Transactional
    public ReviewDTO createHouseReview(HouseReviewData houseReviewData) {

        if (!validationsHouse.existsHouse(houseReviewData.houseId())) {
            throw new EntityNotFoundException("Invalid house ID");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User writer = (User) authentication.getPrincipal();  
        var houseReviewed = houseRepository.findByIdAndIsActiveTrue(houseReviewData.houseId()).get();
        HouseReview houseReview = new HouseReview(houseReviewData, writer, houseReviewed);
        houseReviewed.addStars(houseReview.getStars());
        houseReviewRepository.save(houseReview);
        houseRepository.save(houseReviewed);
        return new ReviewDTO(writer.getEmail(), writer.getName(), houseReviewData.comment(), houseReviewData.stars(), houseReview.getDateTime()); // Placeholder return statement
    }

}
