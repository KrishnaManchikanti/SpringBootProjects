package com.example.MovieTicket.MovieBooking.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.MovieTicket.MovieBooking.Model.Movie;
import com.example.MovieTicket.MovieBooking.service.MovieServiceInterface;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ticket")
public class controller {
	
	@Autowired
	private MovieServiceInterface movieService;

	@GetMapping("/headers")
	public HttpEntity<String> getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Custom-Header", "Value");
		return new HttpEntity<>("Body Content", headers);
	}

	@GetMapping("/response")
	public ResponseEntity<Void> getResponse() {
		return ResponseEntity.status(HttpStatus.OK).build(); // 200 OK with no body
	}



	@RequestMapping("/home")
	public String Welcome() {
		return "Welcome to CodingNinjas Movies";
	}
	
	@GetMapping("/movies")
	public List<Movie> getTodo(){
		
		return movieService.getMovies();
		
	}
	@GetMapping("/movie/{id}")
	@ResponseBody
	public Movie getSingleMovie(@PathVariable String id) {
		
		return movieService.getMovie(id);
	}
	
	@PostMapping("/movie")
	public void addMovies(@Valid @RequestBody Movie movie, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			throw new RuntimeException("Request Not Valid");
		}
		movieService.addMovie(movie);
	}
	 
	@PutMapping("/update/{id}")
	public void updateTodo(@Valid @RequestBody Movie topic,@PathVariable String id) {
		System.out.print(topic);
		movieService.updateMovie(topic,id);

	}
	@DeleteMapping("/movie/{id}")
	public void deleteTodo(@PathVariable String id) {
		movieService.deleteMovie(id);
	}
	
}
