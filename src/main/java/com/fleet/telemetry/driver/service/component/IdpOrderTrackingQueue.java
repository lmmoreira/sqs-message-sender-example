package com.fleet.telemetry.driver.service.component;

import com.fleet.telemetry.driver.controller.dto.TelemetryDTO;
import com.fleet.telemetry.driver.service.flux.Batcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IdpOrderTrackingQueue extends QueuePublisher{

    @Value("${sqs.idp.order.tracking.queue.endpoint}")
    private String sqsQueueEndpoint;

    @Autowired
    Batcher<TelemetryDTO> trackingIdpBatcher;

    @Override boolean isEligible(TelemetryDTO track) {
        return ((track.hasRoute() || track.hasOrders()) && track.isIDP());
    }

    @Override String getQueueEndPoint() {
        return sqsQueueEndpoint;
    }

    @Override Batcher<TelemetryDTO> getBatcher() {
        return trackingIdpBatcher;
    }

}
