package com.fleet.telemetry.driver.factory;

import com.fleet.telemetry.driver.controller.dto.TelemetryDTO;
import com.google.common.collect.Sets;

import java.time.ZonedDateTime;
import java.util.Set;

public class TelemetryDTOFactory {

    public static TelemetryDTO build() {
        return new TelemetryDTOFactory().makeTelemetryDTO();
    }

    private TelemetryDTO makeTelemetryDTO() {
        return new TelemetryDTO(51.4826D, 0.0077D, Byte.parseByte("-64"), ZonedDateTime.now(), ZonedDateTime.now(),
            orders(), orders(), 292054L, 51189L, "WorkerName", "44 32630000", 123.0D, TelemetryDTO.LegTypeDTO.ORIGIN,
            51.4826D, 0.0077D, 10L, TelemetryDTO.TenantDTO.BR);
    }

    private Set<TelemetryDTO.OrderIdDTO> orders() {
        Set<TelemetryDTO.OrderIdDTO> orders = Sets.newHashSet();
        orders.add(new TelemetryDTO.OrderIdDTO(2054070L, "200170206"));
        orders.add(new TelemetryDTO.OrderIdDTO(2054066L, "200170197"));
        orders.add(new TelemetryDTO.OrderIdDTO(2054067L, "200170201"));
        return orders;
    }
}
