package mk.dmt.bus.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCityRequest(
    @NotBlank(message = "City name is required")
    @Size(max = 100, message = "City name must not exceed 100 characters")
    String name,

    @Size(max = 100, message = "Local name must not exceed 100 characters")
    String nameLocal,

    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    String postalCode,

    Double latitude,

    Double longitude,

    @NotNull(message = "Country ID is required")
    Long countryId
) {}

