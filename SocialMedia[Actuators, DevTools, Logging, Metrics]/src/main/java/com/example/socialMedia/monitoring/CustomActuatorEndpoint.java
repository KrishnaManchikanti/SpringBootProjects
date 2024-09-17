package com.example.socialMedia.monitoring;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom Endpoint
 */
@Endpoint(id = "custom")
@Component
public class CustomActuatorEndpoint {

    @ReadOperation
    public Map<String, String> customActuator(String memory){  // http://localhost:8081/admin/custom?memory=12

        Map<String, String> map = new HashMap<>();
        map.put("Compute", "78%");
        map.put("Memory Usage", memory);
        return map;
    }
}
