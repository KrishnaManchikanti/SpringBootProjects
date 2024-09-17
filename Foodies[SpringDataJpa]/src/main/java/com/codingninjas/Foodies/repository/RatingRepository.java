package com.codingninjas.Foodies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codingninjas.Foodies.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

	//NativeQuery
	@Query(value = "SELECT AVG(rating) FROM rating WHERE restaurant_id = :restaurantId", nativeQuery = true)
	double getAverageRatingForRestaurant(@Param("restaurantId") int restaurantId);

}
