package com.fleet.telemetry.driver.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.buffered.AmazonSQSBufferedAsyncClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AmazonSQSConfig {

    @Bean("clientConfiguration")
    public ClientConfiguration clientConfiguration() {
        ClientConfiguration config = new ClientConfiguration();
        config.setSocketTimeout(50);                    //DEFAULT IS 50*1000
        config.setConnectionTimeout(50);                //DEFAULT IS 10*1000
        config.setConnectionMaxIdleMillis(50);          //DEFAULT IS 60*1000
        config.setConnectionTTL(50);                    //DEFAULT IS -1
        config.setRetryPolicy(new RetryPolicy(PredefinedRetryPolicies.DEFAULT_RETRY_CONDITION, PredefinedRetryPolicies.DEFAULT_BACKOFF_STRATEGY, 5, true));
        return config;
    }

    @Bean("sqsAsyncBean")
    @Primary
    public AmazonSQSAsync sqsAsyncBean(ClientConfiguration clientConfiguration) {
        AmazonSQSAsync  sqsAsync = AmazonSQSAsyncClient.asyncBuilder().withClientConfiguration(clientConfiguration).build();
        return new AmazonSQSBufferedAsyncClient(sqsAsync);
    }

}
