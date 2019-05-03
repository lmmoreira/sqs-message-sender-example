package com.fleet.telemetry.driver.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;


@Component
public class TemporaryLog {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemporaryLog.class);

    @Scheduled(fixedRate = 300000)
    public void temporaryLog() {
        LOGGER.info("Fleet telemetry driver is working properly");
    }

}
