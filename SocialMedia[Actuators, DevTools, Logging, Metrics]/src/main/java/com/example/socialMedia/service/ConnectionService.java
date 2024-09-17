package com.example.socialMedia.service;

import java.util.List;
import java.util.Optional;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.socialMedia.model.Connection;
import com.example.socialMedia.repository.ConnectionDal;

@Service
public class ConnectionService {

	private Counter connectionCallCounter = null;

	public ConnectionService(CompositeMeterRegistry compositeMeterRegistry) {
		this.connectionCallCounter = compositeMeterRegistry.counter("connection.call.counter");
	}

	@Autowired
	ConnectionDal connectionDao;

	Logger logger = LoggerFactory.getLogger(ConnectionService.class);

	public List<Connection> getConnections() {
		logger.trace("TRACING: Connections are on the way....");
		connectionCallCounter.increment();
		return connectionDao.findAll();
	}

	public List<Connection> getConnectionsByCompany(String company) {
		return connectionDao.findByCompany(company);
	}
  
	public Connection addConnection(Connection connection) {
		return connectionDao.save(connection);
	}

}
