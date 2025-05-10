package edu.ucaldas.back.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ucaldas.back.models.review.UserReviewData;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @GetMapping("user/{email}")
    public String getUserReview(@PathVariable String email,@PageableDefault(size = 10) Pageable page) {
        return "User review data";
    }

    @GetMapping("house/{id}")
    public String getHouseReview(@PathVariable Long id) {
        return "House review data";
    }

    @PostMapping("user")
    public String createUserReview(@RequestBody @Valid UserReviewData userReview) {
        return "User review created";
    }

    @PostMapping("house")
    public String createHouseReview(@RequestBody @Valid UserReviewData userReview) {
        return "House review created";
    }

}
