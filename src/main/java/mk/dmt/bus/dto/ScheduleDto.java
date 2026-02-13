package mk.dmt.bus.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleDto(
    Long id,
    LocalTime departureTime,
    LocalTime arrivalTime,
    DayOfWeek dayOfWeek,
    Boolean isActive,
    LocalDate validFrom,
    LocalDate validUntil,
    String notes,
    Long busLineId,
    String busLineName
) {}

