package com.fleet.telemetry.driver.service.component;

import static org.slf4j.LoggerFactory.getLogger;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchResult;
import org.slf4j.Logger;

public class CustomHandler implements AsyncHandler<SendMessageBatchRequest, SendMessageBatchResult> {

    private static final Logger logger = getLogger(CustomHandler.class);

    public void onError(Exception exception){
        logger.error(exception.getMessage());
    }

    public void onSuccess(SendMessageBatchRequest request, SendMessageBatchResult result){
        logger.debug(result.toString());
    }

}
