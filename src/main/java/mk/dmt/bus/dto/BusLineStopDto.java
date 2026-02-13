package mk.dmt.bus.dto;

public record BusLineStopDto(
    Long id,
    Integer stopOrder,
    Integer arrivalOffsetMinutes,
    Integer departureOffsetMinutes,
    Integer distanceFromOriginKm,
    Long busStationId,
    String busStationName,
    String cityName
) {}

