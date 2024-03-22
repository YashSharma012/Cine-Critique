package com.yash.MovieRoger.repository;

import com.yash.MovieRoger.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "select avg(rating) from reviews where movie_id = ?", nativeQuery = true)
    Double getReviewAverage(Long id);
}
