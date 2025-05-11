package edu.ucaldas.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ucaldas.back.DTO.ReviewDTO;
import edu.ucaldas.back.models.review.HouseReviewData;
import edu.ucaldas.back.models.review.UserReviewData;
import edu.ucaldas.back.service.HouseReviewService;
import edu.ucaldas.back.service.UserReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private HouseReviewService houseReviewService;
    @Autowired
    private UserReviewService userReviewService;


    @GetMapping("user/{email}")
    public Page<ReviewDTO> getUserReview(@PathVariable @Valid @Email String email,@PageableDefault(size = 10) Pageable page) {
        return userReviewService.getUserReviews(email, page);
    }

    @GetMapping("house/{id}")
    public Page<ReviewDTO> getHouseReview(@PathVariable Long id ,@PageableDefault(size = 10) Pageable page) {
        return houseReviewService.getReviewsHouse(id, page);
    }

    @PostMapping("user")
    public ReviewDTO createUserReview(@RequestBody @Valid UserReviewData userReview) {
        return userReviewService.createUserReview(userReview);
    }

    @PostMapping("house")
    public ReviewDTO createHouseReview(@RequestBody @Valid HouseReviewData houseReviewData) {
        return houseReviewService.createHouseReview(houseReviewData);
    }

}
