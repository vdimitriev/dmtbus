package mk.dmt.bus.dto;

import java.util.List;

public record BusLineDto(
    Long id,
    String lineNumber,
    String name,
    String description,
    Boolean isInternational,
    Boolean isActive,
    Integer distanceKm,
    Integer estimatedDurationMinutes,
    Long busCompanyId,
    String busCompanyName,
    BusStationDto originStation,
    BusStationDto destinationStation,
    List<BusLineStopDto> stops
) {}

