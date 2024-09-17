package com.codingninjas.Foodies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codingninjas.Foodies.entity.Customer;
import com.codingninjas.Foodies.entity.Restaurant;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
	//DerivedQuery
	List<Customer> findByVisitedRestaurants(Restaurant restaurant);
	//JPQL Query
	@Query("SELECT DISTINCT c FROM Customer c JOIN c.visitedRestaurants r JOIN c.ratings ra WHERE r.name = :restaurantName AND ra.rating > :rating")
	List<Customer> findByVisitedRestaurantAndRating(@Param("restaurantName") String restaurantName, @Param("rating") double ratingValue);


}
