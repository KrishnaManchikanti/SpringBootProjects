package com.example.MovieTicket.MovieBooking.communicator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
//Communication with RatingApplication through REST TEMPLATE
@Service
public class RatingRestCommunicator {

    RestTemplate restTemplate;

    @Autowired
    public RatingRestCommunicator(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public long getRating(String id) {
        String url = "http://localhost:8081/rating/id/";

        ResponseEntity<Long> responseEntity =  restTemplate.exchange(url+id,HttpMethod.GET,null,Long.class);
        if (responseEntity.getBody() != null) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Failed to retrieve rating for ID: " + id);
        }
    }

    public void addRating(Map<String, Long> ratingsMap) {
        String url = "http://localhost:8081/rating/add";
        HttpEntity<Map<String, Long>> requestEntity  = new HttpEntity<>(ratingsMap);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(url,HttpMethod.POST, requestEntity, Void.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Rating added successfully");
        } else {
            System.out.println("Failed to add rating: " + responseEntity.getStatusCode());
        }
    }

    public void updateRating(Map<String, Long> ratingsMap) {
        String url = "http://localhost:8081/rating/update";
        HttpEntity<Map<String, Long>> requestEntity  = new HttpEntity<>(ratingsMap);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(url,HttpMethod.PUT, requestEntity, Void.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Rating updated successfully");
        } else {
            System.out.println("Failed to update rating: " + responseEntity.getStatusCode());
        }
    }

    public void deleteRating(String id) {
        String url = "http://localhost:8081/rating/id/";

        ResponseEntity<Void> responseEntity = restTemplate.exchange(url+id,HttpMethod.DELETE, null, Void.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Rating deleted successfully");
        } else {
            System.out.println("Failed to delete rating: " + responseEntity.getStatusCode());
        }
    }

}
