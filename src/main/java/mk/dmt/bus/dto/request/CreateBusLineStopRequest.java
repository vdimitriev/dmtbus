package mk.dmt.bus.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateBusLineStopRequest(
    @NotNull(message = "Stop order is required")
    Integer stopOrder,

    Integer arrivalOffsetMinutes,

    Integer departureOffsetMinutes,

    Integer distanceFromOriginKm,

    @NotNull(message = "Bus station ID is required")
    Long busStationId
) {}

