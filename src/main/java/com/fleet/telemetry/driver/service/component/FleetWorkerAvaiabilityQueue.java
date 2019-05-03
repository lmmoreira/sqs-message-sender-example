package com.fleet.telemetry.driver.service.component;

import com.fleet.telemetry.driver.controller.dto.TelemetryDTO;
import com.fleet.telemetry.driver.service.flux.Batcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FleetWorkerAvaiabilityQueue extends QueuePublisher {

    @Value("${sqs.fleet.worker.availability.queue.endpoint}")
    private String sqsQueueEndpoint;

    @Autowired
    Batcher<TelemetryDTO> trackingAvaiabilityBatcher;

    @Override boolean isEligible(TelemetryDTO track) {
        return (true);
    }

    @Override String getQueueEndPoint() {
        return sqsQueueEndpoint;
    }

    @Override Batcher<TelemetryDTO> getBatcher() {
        return trackingAvaiabilityBatcher;
    }

}
