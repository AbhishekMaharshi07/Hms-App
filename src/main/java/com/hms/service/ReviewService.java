package com.hms.service;

import com.hms.entity.AppUser;
import com.hms.entity.Property;
import com.hms.entity.Review;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.PropertyRepository;
import com.hms.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private PropertyRepository propertyRepository;
    private ReviewRepository reviewRepository;

    public ReviewService(PropertyRepository propertyRepository, ReviewRepository reviewRepository) {
        this.propertyRepository = propertyRepository;
        this.reviewRepository = reviewRepository;
    }


    public Review writeReview(Review review, long propertyId, AppUser user) {
        Property property = propertyRepository.findById(propertyId).get();
        if(reviewRepository.existsByAppUserAndProperty
                (user, property)){
            throw new IllegalArgumentException("Review already exists for this property");
        }
        review.setProperty(property);
        review.setAppUser(user);
        Review savedReview = reviewRepository.save(review);
        return savedReview;
        }
    }

