package com.example.socialMedia.monitoring;

import com.example.socialMedia.repository.ConnectionDal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Customizing expose endpoint
 * Writing own custom logic to determine the Health of the DB application
 */
@Component
public class DataBaseMonitorService implements HealthIndicator {

@Autowired
    ConnectionDal connectionDal;

    @Override
    public Health health() {
        if(isDatabaseHealthy())
            return Health.up().withDetail("Connection Database", "up& running").build();
        return Health.down().withDetail("Connection Database", "down").build();
    }

    private boolean isDatabaseHealthy() {
        try {
            connectionDal.findById(1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
