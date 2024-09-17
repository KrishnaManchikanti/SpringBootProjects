package com.example.socialMedia.monitoring;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * For creating custom metric
 */
@Configuration
public class MetricConfiguration {
    @Bean
    public MeterRegistry getMeterRegistry(){
        return new CompositeMeterRegistry();
    }
}
