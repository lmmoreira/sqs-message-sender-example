package com.fleet.telemetry.driver.controller.dto;

import static java.util.Objects.nonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Set;
import java.util.StringJoiner;

import javax.validation.constraints.NotNull;

public class TelemetryDTO {

    public enum LegTypeDTO {
        ORIGIN, DESTINATION;
    }

    public enum TenantDTO {
        BR, MX;
    }

    public static class OrderIdDTO {
        private Long orderId;

        private String externalId;

        @JsonCreator
        public OrderIdDTO(@JsonProperty("orderId") final Long orderId, @JsonProperty("externalId") final String externalId) {
            this.orderId = orderId;
            this.externalId = externalId;
        }

        public Long getOrderId() {
            return orderId;
        }

        public String getExternalId() {
            return externalId;
        }

        @Override public String toString() {
            return "OrderIdDTO{" + "orderId=" + orderId + ", externalId='" + externalId + '\'' + '}';
        }
    }

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    private Byte batteryStatus;

    @NotNull
    private ZonedDateTime trackDate;

    @NotNull
    private ZonedDateTime eventDate;

    private ZonedDateTime serverDate;

    private Set<OrderIdDTO> orders;

    private Set<OrderIdDTO> currentOrders;

    private Long routeId;

    private Long workerId;

    private String workerName;

    private String workerPhone;

    private Double legCorrectionFactor;

    private LegTypeDTO legType;

    private Double serviceLatitude;

    private Double serviceLongitude;

    private Long speed;

    private TenantDTO tenant;

    @JsonCreator
    public TelemetryDTO(@JsonProperty(value = "latitude", required = true) final Double latitude,
                        @JsonProperty(value = "longitude", required = true) final Double longitude, @JsonProperty("batteryStatus") final Byte batteryStatus,
                        @JsonProperty(value = "trackDate", required = true) final ZonedDateTime trackDate,
                        @JsonProperty("eventDate") final ZonedDateTime eventDate,
                        @JsonProperty("orders") final Set<OrderIdDTO> orders,
                        @JsonProperty("currentOrders") final Set<OrderIdDTO> currentOrders,
                        @JsonProperty("routeId") final Long routeId, @JsonProperty("workerId") final Long workerId,
                        @JsonProperty("workerName") final String workerName, @JsonProperty("workerPhone") final String workerPhone,
                        @JsonProperty("legCorrectionFactor") final Double legCorrectionFactor,
                        @JsonProperty("legType") final LegTypeDTO legType,
                        @JsonProperty("serviceLatitude") final Double serviceLatitude,
                        @JsonProperty("serviceLongitude") final Double serviceLongitude, @JsonProperty("speed") final Long speed,
                        @JsonProperty(value = "tenant", required = true) final TenantDTO tenant) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.batteryStatus = batteryStatus;
        this.trackDate = trackDate;
        this.eventDate = eventDate;
        this.orders = MoreObjects.firstNonNull(orders, Collections.emptySet());
        this.currentOrders = MoreObjects.firstNonNull(currentOrders, Collections.emptySet());
        this.routeId = routeId;
        this.workerId = workerId;
        this.workerName = workerName;
        this.workerPhone = workerPhone;
        this.legCorrectionFactor = legCorrectionFactor;
        this.legType = legType;
        this.serviceLatitude = serviceLatitude;
        this.serviceLongitude = serviceLongitude;
        this.speed = speed;
        this.tenant = tenant;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Byte getBatteryStatus() {
        return batteryStatus;
    }

    public ZonedDateTime getEventDate() {
        return eventDate;
    }

    public ZonedDateTime getTrackDate() {
        return trackDate;
    }

    public Set<OrderIdDTO> getOrders() {
        return orders;
    }

    public Set<OrderIdDTO> getCurrentOrders() {
        return currentOrders;
    }

    public Long getRouteId() {
        return routeId;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getWorkerPhone() {
        return workerPhone;
    }

    public Double getLegCorrectionFactor() {
        return legCorrectionFactor;
    }

    public Boolean isLegCorrectionFactorGreaterThanZero() { return nonNull(getLegCorrectionFactor()) && getLegCorrectionFactor() > 0; }

    public LegTypeDTO getLegType() {
        return legType;
    }

    public Double getServiceLatitude() {
        return serviceLatitude;
    }

    public boolean isServiceLatitudeNotNull() {
        return serviceLatitude != null;
    }

    public boolean isServiceLongitudeNotNull() {
        return serviceLongitude != null;
    }

    public Double getServiceLongitude() {
        return serviceLongitude;
    }

    public Long getSpeed() {
        return speed;
    }

    public Boolean isSpeedGreaterThanZero() {
        return nonNull(getSpeed()) && getSpeed() > 0;
    }

    public TenantDTO getTenant() {
        return tenant;
    }

    public boolean hasRoute() {
        return this.getRouteId() != null;
    }

    public boolean hasOrders() {
        return !this.getOrders().isEmpty();
    }

    public boolean isIDP() {
        return isLegCorrectionFactorGreaterThanZero() && isSpeedGreaterThanZero() && isServiceLatitudeNotNull() && isServiceLongitudeNotNull();
    }

    public boolean isBatteryBlock(Byte minBatteryStatus) {
        return getBatteryStatus() < minBatteryStatus;
    }

    public boolean isClockBlock(int maxSecondsDiff) { return ChronoUnit.SECONDS.between(getEventDate(), getEventDate()) > maxSecondsDiff; }

    public ZonedDateTime getServerDate() {
        return serverDate;
    }

    public void setServerDate(final ZonedDateTime serverDate) {
        this.serverDate = serverDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TelemetryDTO.class.getSimpleName() + "[", "]").add("latitude=" + latitude)
                .add("longitude=" + longitude)
                .add("batteryStatus=" + batteryStatus)
                .add("trackDate=" + trackDate)
                .add("eventDate=" + eventDate)
                .add("serverDate=" + serverDate)
                .add("orders=" + orders)
                .add("currentOrders=" + currentOrders)
                .add("routeId=" + routeId)
                .add("workerId=" + workerId)
                .add("workerName='" + workerName + "'")
                .add("workerPhone='" + workerPhone + "'")
                .add("legCorrectionFactor=" + legCorrectionFactor)
                .add("legType=" + legType)
                .add("serviceLatitude=" + serviceLatitude)
                .add("serviceLongitude=" + serviceLongitude)
                .add("speed=" + speed)
                .add("tenant=" + tenant)
                .toString();
    }
}
