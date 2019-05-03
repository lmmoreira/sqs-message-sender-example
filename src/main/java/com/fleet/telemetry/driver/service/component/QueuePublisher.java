package com.fleet.telemetry.driver.service.component;

import static java.lang.String.valueOf;
import static org.slf4j.LoggerFactory.getLogger;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.fleet.telemetry.driver.controller.dto.TelemetryDTO;
import com.fleet.telemetry.driver.service.flux.Batcher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import javax.annotation.PostConstruct;

import reactor.core.scheduler.Schedulers;

public abstract class QueuePublisher {

    private static final Logger logger = getLogger(QueuePublisher.class);

    @Autowired
    private AmazonSQSAsync sqsAsync;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Value("${attribute.tenant.type:String}")
    private String attributeTenantType;

    @Value("${attribute.tenant.name:tenant-identifier}")
    private String attributeTenantName;

    @PostConstruct
    public void subscribe() {
        getBatcher().listen().subscribeOn(Schedulers.elastic()).subscribe(this::sendToAws);
    }

    abstract Batcher<TelemetryDTO> getBatcher();

    abstract String getQueueEndPoint();

    abstract boolean isEligible(TelemetryDTO track);

    public void sendMessage(TelemetryDTO track) {
        if (isEligible(track)) {
            getBatcher().addToBatch(track);
        }
    }

    private void sendToAws(List<TelemetryDTO> elements) {
        List<SendMessageBatchRequestEntry> entries = Lists.newArrayList();

        for (TelemetryDTO telemetry : elements) {
            try {
                entries.add(createEntry(telemetry));
            }catch (JsonProcessingException ex){
                logger.error("Error adding TelemetryDTO to list", ex);
            }
        }

        logger.debug("Sending batch message to {}", getQueueEndPoint());

        sqsAsync.sendMessageBatchAsync(getQueueEndPoint(), entries, new CustomHandler());
    }

    private SendMessageBatchRequestEntry createEntry(TelemetryDTO telemetry) throws JsonProcessingException {
        final String json = jacksonObjectMapper.writeValueAsString(telemetry);

        MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.setDataType(attributeTenantType);
        messageAttributeValue.setStringValue(telemetry.getTenant().toString().toLowerCase());

        return new SendMessageBatchRequestEntry()
                .withId(valueOf(json.hashCode()))
                .withMessageBody(json)
                .addMessageAttributesEntry(attributeTenantName, messageAttributeValue);
    }

}
