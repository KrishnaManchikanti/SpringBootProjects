package com.example.MovieTicket.MovieBooking.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.MovieTicket.MovieBooking.Exceptions.IdAlreadyExist;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.example.MovieTicket.MovieBooking.Exceptions.IdNotFound;
import com.example.MovieTicket.MovieBooking.Model.Movie;

@Service
public class MovieService implements MovieServiceInterface {
	
	List<Movie> moviesList =new ArrayList<>();
	Map<String,Movie> movieMap= new HashMap<>();


	@Override
	public List<Movie> getMovies() {
		return moviesList;
	}

	@Override
	public Movie getMovie(String id) {
		
		if(ObjectUtils.isEmpty(movieMap.get(id)))
		{
			throw new IdNotFound("Hotel not found for id: "+id);
		}
		Movie movie = movieMap.get(id);
		return movie;
	}

	@Override
	public void addMovie(Movie movie) {
		if(movieMap.containsKey(movie.getId())){
		throw new IdAlreadyExist("Movie with id " + movie.getId()+" already exist");
		}
		moviesList.add(movie);
		movieMap.put(movie.getId(), movie);
	}
		

	@Override
	public void deleteMovie(String id) {
		Movie movie = getMovie(id);
		moviesList.remove(movie);
		movieMap.remove(id);
	}

	@Override
	public void updateMovie(Movie movie, String id) {
        Movie existingMovie= getMovie(id);
		
		moviesList.remove(existingMovie);
		moviesList.add(movie);		
		movieMap.put(id, movie);
	}

}
