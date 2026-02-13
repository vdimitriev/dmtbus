package mk.dmt.bus.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public record CreateScheduleRequest(
    @NotNull(message = "Departure time is required")
    LocalTime departureTime,

    @NotNull(message = "Arrival time is required")
    LocalTime arrivalTime,

    @NotNull(message = "Day of week is required")
    DayOfWeek dayOfWeek,

    LocalDate validFrom,

    LocalDate validUntil,

    String notes,

    @NotNull(message = "Bus line ID is required")
    Long busLineId
) {}

