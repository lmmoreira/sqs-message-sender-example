package com.fleet.telemetry.driver;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleet.telemetry.driver.controller.dto.TelemetryDTO;
import com.fleet.telemetry.driver.factory.TelemetryDTOFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

class TelemetryDTOTest extends BaseIntegrationTest {

    @Value("${min.battery.status:20}")
    private Byte minBatteryStatus;

    @Value("${max.seconds.diff:20}")
    private Integer maxSecondsDiff;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Test
    public void isBatteryBlackTest() {
        final TelemetryDTO telemetryDTO = TelemetryDTOFactory.build();
        Assertions.assertTrue(telemetryDTO.isBatteryBlock(this.minBatteryStatus));
    }

    @Test
    public void isClockBlockTest() {
        final TelemetryDTO telemetryDTO = TelemetryDTOFactory.build();
        Assertions.assertFalse(telemetryDTO.isClockBlock(minBatteryStatus));
    }

    @Test
    public void hasRouteTest() {
        final TelemetryDTO telemetryDTO = TelemetryDTOFactory.build();
        Assertions.assertTrue(telemetryDTO.hasRoute());
    }

    @Test
    public void hasOrdersTest() {
        final TelemetryDTO telemetryDTO = TelemetryDTOFactory.build();
        Assertions.assertTrue(telemetryDTO.hasOrders());
    }

    @Test
    public void isIDPTest() {
        final TelemetryDTO telemetryDTO = TelemetryDTOFactory.build();
        Assertions.assertTrue(telemetryDTO.isIDP());
    }

    @Test
    public void isSpeedGreaterThanZeroTest() {
        final TelemetryDTO telemetryDTO = TelemetryDTOFactory.build();
        Assertions.assertTrue(telemetryDTO.isSpeedGreaterThanZero());
    }

    @Test
    public void serializeNoTenant(){

        String payload =    "{\n" +
                            "   \"latitude\":51.4826,\n" +
                            "   \"longitude\":0.0077,\n" +
                            "   \"batteryStatus\":-64,\n" +
                            "   \"trackDate\":\"2019-02-04T10:51:37.557-02:00\",\n" +
                            "   \"eventDate\":\"2019-02-04T10:51:37.557-02:00\",\n" +
                            "   \"orders\":[\n" +
                            "      {\n" +
                            "         \"orderId\":2054070,\n" +
                            "         \"externalId\":\"200170206\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"orderId\":2054071,\n" +
                            "         \"externalId\":\"200170206\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"orderId\":2054067,\n" +
                            "         \"externalId\":\"200170201\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"orderId\":2054073,\n" +
                            "         \"externalId\":\"200170206\"\n" +
                            "      }\n" +
                            "\n" +
                            "   ],\n" +
                            "   \"currentOrders\":[\n" +
                            "      {\n" +
                            "         \"orderId\":2054066,\n" +
                            "         \"externalId\":\"200170197\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"orderId\":2054067,\n" +
                            "         \"externalId\":\"200170201\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"orderId\":2054066,\n" +
                            "         \"externalId\":\"200170197\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"orderId\":2054070,\n" +
                            "         \"externalId\":\"200170206\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"orderId\":2054067,\n" +
                            "         \"externalId\":\"200170201\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"orderId\":2054066,\n" +
                            "         \"externalId\":\"200170197\"\n" +
                            "      }\n" +
                            "   ],\n" +
                            "   \"routeId\":292054,\n" +
                            "   \"workerId\":51189,\n" +
                            "   \"workerName\":\"WorkerName\",\n" +
                            "   \"workerPhone\":\"12211\",\n" +
                            "   \"legCorrectionFactor\":123.0,\n" +
                            "   \"legType\":\"ORIGIN\",\n" +
                            "   \"serviceLatitude\":51.4826,\n" +
                            "   \"serviceLongitude\":0.0077,\n" +
                            "   \"speed\":10\n" +
                            //"   \"tenant\":\"BR\"\n" +
                            "}";

        assertThrows(IOException.class, () -> jacksonObjectMapper.readValue(payload, TelemetryDTO.class));

    }

}
