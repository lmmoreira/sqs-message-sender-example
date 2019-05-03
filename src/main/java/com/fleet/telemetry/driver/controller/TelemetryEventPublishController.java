package com.fleet.telemetry.driver.controller;

import static org.slf4j.LoggerFactory.getLogger;

import com.fleet.telemetry.driver.controller.dto.TelemetryDTO;
import com.fleet.telemetry.driver.service.TelemetryEventPublishService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
public class TelemetryEventPublishController {

    private static final Logger logger = getLogger(TelemetryEventPublishController.class);

    @Autowired
    private TelemetryEventPublishService telemetryEventPublishService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void locationPublish(@Valid @RequestBody TelemetryDTO track) {
        telemetryEventPublishService.publish(track);
    }

}
