package mk.dmt.bus.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public record SearchResultDto(
    Long busLineId,
    String busLineName,
    String busCompanyName,
    String originCity,
    String destinationCity,
    LocalTime departureTime,
    LocalTime arrivalTime,
    DayOfWeek dayOfWeek,
    Integer durationMinutes,
    Integer distanceKm,
    Boolean isInternational,
    List<BusLineStopDto> stops
) {}

