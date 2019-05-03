package com.fleet.telemetry.driver.service;

import com.fleet.telemetry.driver.controller.dto.TelemetryDTO;
import com.fleet.telemetry.driver.service.component.FleetOrderTrackingDynamoDbQueue;
import com.fleet.telemetry.driver.service.component.FleetWorkerAvaiabilityQueue;
import com.fleet.telemetry.driver.service.component.IdpOrderTrackingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class TelemetryEventPublishService {

    @Autowired
    private FleetOrderTrackingDynamoDbQueue fleetOrderTrackingDynamoDbQueue;

    @Autowired
    private IdpOrderTrackingQueue idpOrderTrackingQueue;

    @Autowired
    private FleetWorkerAvaiabilityQueue fleetWorkerAvaiabilityQueue;

    public void publish(TelemetryDTO track) {
        track.setServerDate(ZonedDateTime.now());
        fleetWorkerAvaiabilityQueue.sendMessage(track);
        fleetOrderTrackingDynamoDbQueue.sendMessage(track);
        idpOrderTrackingQueue.sendMessage(track);
    }
}
