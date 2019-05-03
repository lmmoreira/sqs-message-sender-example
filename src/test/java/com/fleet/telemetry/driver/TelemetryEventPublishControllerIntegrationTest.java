package com.fleet.telemetry.driver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fleet.telemetry.driver.factory.TelemetryDTOFactory;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

class TelemetryEventPublishControllerIntegrationTest extends BaseIntegrationTest {

    @Value("${size.to.send.messages.in.batch:10}")
    private Integer sizeToSendMessages;

    @MockBean
    private AmazonSQSAsync sqsAsyncBean;

    @Test
    void withSuccessTest() {

        // @formatter:off
        RestAssured
            .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(TelemetryDTOFactory.build())
            .when()
                .post("/api/telemetry/location")
            .then().log().all()
                .statusCode(HttpStatus.SC_ACCEPTED);
        // @formatter:on

    }

    @Test
    void withSuccessManyTimes() {
        for (int i = 1; i <= sizeToSendMessages; i++) {
            withSuccessTest();

            if (i == sizeToSendMessages) {
                verify(sqsAsyncBean, atLeastOnce()).sendMessageBatchAsync(Mockito.anyString(), Mockito.anyList(), any());
            }
        }
    }

}
