package mk.dmt.bus.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CreateBusLineRequest(
    @Size(max = 50, message = "Line number must not exceed 50 characters")
    String lineNumber,

    @NotBlank(message = "Line name is required")
    @Size(max = 200, message = "Line name must not exceed 200 characters")
    String name,

    @Size(max = 500, message = "Description must not exceed 500 characters")
    String description,

    Boolean isInternational,

    Integer distanceKm,

    Integer estimatedDurationMinutes,

    @NotNull(message = "Bus company ID is required")
    Long busCompanyId,

    @NotNull(message = "Origin station ID is required")
    Long originStationId,

    @NotNull(message = "Destination station ID is required")
    Long destinationStationId,

    List<CreateBusLineStopRequest> stops
) {}

