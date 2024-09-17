package com.example.MovieTicket.MovieBooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.MovieTicket.MovieBooking.Model.Movie;

public interface MovieServiceInterface {

	Movie getMovie(String id);

	void addMovie(Movie todo);

	void deleteMovie(String id);

	List<Movie> getMovies();

	void updateMovie(Movie movie, String id);

}
