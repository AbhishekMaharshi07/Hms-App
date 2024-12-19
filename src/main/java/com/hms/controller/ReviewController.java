package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.entity.Property;
import com.hms.entity.Review;
import com.hms.repository.PropertyRepository;
import com.hms.repository.ReviewRepository;
import com.hms.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private PropertyRepository propertyRepository;
    private ReviewService reviewService;
    private ReviewRepository reviewRepository;

    public ReviewController(PropertyRepository propertyRepository, ReviewService reviewService, ReviewRepository reviewRepository) {
        this.propertyRepository = propertyRepository;
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
    }

    //http://localhost:8080/api/v1/review?propertyId=1
    @PostMapping
    public ResponseEntity<?> writeReview(@RequestBody Review review,
                                              @RequestParam long propertyId,
                                              @AuthenticationPrincipal AppUser user)
    {
        try {
            Review savedReview = reviewService.writeReview(review, propertyId, user);

            return new ResponseEntity<>(savedReview, HttpStatus.OK);

        }catch (IllegalArgumentException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/user/review")
    public ResponseEntity<List<Review>> getUserReviews(
            @AuthenticationPrincipal AppUser user
    ) {
        List<Review> reviews = reviewRepository.findByAppUser(user);
        return new ResponseEntity(reviews, HttpStatus.OK);

    }
}
