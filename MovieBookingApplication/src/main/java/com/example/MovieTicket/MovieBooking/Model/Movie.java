package com.example.MovieTicket.MovieBooking.Model;

import java.util.List;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//
//The class contains the validation over here so that we can validate the entry type when we are using postMapping in controller
//
public class Movie {

	@Size(min = 1)
	public String id;

	@Size(min = 3, max = 20)
	public String movieName;

	@NotNull(message = "cannot be null")
	public String movieDirector;
	
	@Min(value = 1)
	@Max(value = 10)
	public long movieRating;
	  
	public String movieLanguage;
	public List<String> writers;
	public List<String> actors;
	public List<String> genre;

	public Movie (){

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getMovieDirector() {
		return movieDirector;
	}

	public void setMovieDirector(String movieDirector) {
		this.movieDirector = movieDirector;
	}

	public  long getMovieRating() {
		return movieRating;
	}

	public void setMovieRating(long updatedRating) {
		this.movieRating = updatedRating;
	}

	public String getMovieLanguage() {
		return movieLanguage;
	}

	public void setMovieLanguage(String movieLanguage) {
		this.movieLanguage = movieLanguage;
	}

	public List<String> getWriters() {
		return writers;
	}

	public void setWriters(List<String> writers) {
		this.writers = writers;
	}

	public List<String> getActors() {
		return actors;
	}

	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	public List<String> getGenre() {
		return genre;
	}

	public void setGenre(List<String> genre) {
		this.genre = genre;
	}


	

}
