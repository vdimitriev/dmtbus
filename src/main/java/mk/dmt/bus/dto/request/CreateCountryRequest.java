package mk.dmt.bus.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCountryRequest(
    @NotBlank(message = "Country code is required")
    @Size(min = 2, max = 3, message = "Country code must be 2-3 characters")
    String code,

    @NotBlank(message = "Country name is required")
    @Size(max = 100, message = "Country name must not exceed 100 characters")
    String name,

    @Size(max = 100, message = "Local name must not exceed 100 characters")
    String nameLocal
) {}

