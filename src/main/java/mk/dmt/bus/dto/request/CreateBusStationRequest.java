package mk.dmt.bus.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateBusStationRequest(
    @NotBlank(message = "Station name is required")
    @Size(max = 150, message = "Station name must not exceed 150 characters")
    String name,

    @Size(max = 150, message = "Local name must not exceed 150 characters")
    String nameLocal,

    @Size(max = 255, message = "Address must not exceed 255 characters")
    String address,

    Double latitude,

    Double longitude,

    @Size(max = 50, message = "Phone number must not exceed 50 characters")
    String phoneNumber,

    Boolean isMainStation,

    @NotNull(message = "City ID is required")
    Long cityId
) {}

