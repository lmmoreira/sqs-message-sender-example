package com.fleet.telemetry.driver.service.component;

import com.fleet.telemetry.driver.controller.dto.TelemetryDTO;
import com.fleet.telemetry.driver.service.flux.Batcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FleetOrderTrackingDynamoDbQueue extends QueuePublisher {

    @Value("${sqs.fleet.order.tracking.dynamodb.queue.endpoint}")
    private String sqsQueueEndpoint;

    @Autowired
    Batcher<TelemetryDTO> trackingDynamoDbBatcher;

    @Override boolean isEligible(TelemetryDTO track) {
        return (track.hasRoute() && track.hasOrders());
    }

    @Override String getQueueEndPoint() {
        return sqsQueueEndpoint;
    }

    @Override Batcher<TelemetryDTO> getBatcher() {
        return trackingDynamoDbBatcher;
    }

}
