package com.soct.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.soct.event.model.Review;
import com.soct.event.repository.ReviewRepository;
import com.soct.event.repository.RegistrationRepository;
import com.soct.event.repository.EventRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SentimentService sentimentService; // <-- injected here

    // Add Review with automatic sentiment analysis
    public Review addReview(Review review) {

        // Normalize safely
        String studentId = normalize(review.getStudentId());
        String eventId = review.getEventId();

        // Null validation
        if (studentId == null || eventId == null) {
            throw new RuntimeException("Missing required fields");
        }

        // 1. Check event exists
        if (!eventRepository.existsById(eventId)) {
            throw new RuntimeException("Invalid event ID");
        }

        // 2. Only registered users can review
        if (!registrationRepository.existsByEventIdAndStudentId(eventId, studentId)) {
            throw new RuntimeException("You must attend the event to leave a review");
        }

        // 3. Prevent duplicate reviews
        if (reviewRepository.existsByEventIdAndStudentId(eventId, studentId)) {
            throw new RuntimeException("You already reviewed this event");
        }

        // 4. Validate rating range
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        review.setEventId(eventId);
        review.setStudentId(studentId);
        review.setTimestamp(LocalDateTime.now().toString());

        // 5. Run sentiment analysis on the comment (if provided)
        String comment = review.getComment();
        if (comment != null && !comment.isBlank()) {
            String sentiment = sentimentService.analyzeSentiment(comment);
            review.setSentiment(sentiment);
        } else {
            review.setSentiment("NEUTRAL");
        }

        return reviewRepository.save(review);
    }

    // Get reviews for an event
    public List<Review> getReviewsByEvent(String eventId) {
        return reviewRepository.findByEventId(eventId);
    }

    public String normalize(String value) {
        return value == null ? null : value.trim().toUpperCase();
    }

    // Average rating
    public double getAverageRating(String eventId) {
        List<Review> reviews = reviewRepository.findByEventId(eventId);
        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0);
    }
}