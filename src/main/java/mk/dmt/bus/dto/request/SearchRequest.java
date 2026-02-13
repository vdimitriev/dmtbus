package mk.dmt.bus.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;

public record SearchRequest(
    @NotNull(message = "Origin city ID is required")
    Long originCityId,

    @NotNull(message = "Destination city ID is required")
    Long destinationCityId,

    LocalDate travelDate,

    DayOfWeek dayOfWeek
) {}

