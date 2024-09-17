package com.example.socialMedia.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialMedia.dto.ConnectionResponseDto;
import com.example.socialMedia.model.Connection;
import com.example.socialMedia.repository.ConnectionDal;
import com.example.socialMedia.service.ConnectionService;

@RestController
@RequestMapping("/ninjas")
public class userController {
//slf4j
	Logger logger = LoggerFactory.getLogger(userController.class);

	@Autowired
	ConnectionService connectionService;
	
	@Autowired
	ConnectionDal connectionDao;
	
	@GetMapping("/test")
	public String test() {
		return "test success!";
	}
	
	@GetMapping("/connections")
	public List<Connection> getConnections() {
		/**
		 * Not recommend printing response in log #LearningPurpose
		 */
        logger.info("Response: Started to get Connections! {}", connectionService.getConnections());
		return connectionService.getConnections();
	}
	
	@GetMapping("/connections/{company}")
	public List<Connection> getConnectionsByCompany(@PathVariable String company){

		List<Connection> connections = connectionService.getConnectionsByCompany(company);
		try {
			if(connections.isEmpty()){ throw new Exception();}
        } catch (Exception e) {
			logger.error("Exception:  Received Empty Response");
        }
		return connections;
    }
	
	@PostMapping("/add")
	public Connection addConnection(@RequestBody Connection connection) {
		return connectionService.addConnection(connection);
	}
	
}
