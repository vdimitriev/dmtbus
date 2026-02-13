package mk.dmt.bus.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBusCompanyRequest(
    @NotBlank(message = "Company name is required")
    @Size(max = 150, message = "Company name must not exceed 150 characters")
    String name,

    @Size(max = 50, message = "Registration number must not exceed 50 characters")
    String registrationNumber,

    @Size(max = 255, message = "Address must not exceed 255 characters")
    String address,

    @Size(max = 50, message = "Phone number must not exceed 50 characters")
    String phoneNumber,

    @Size(max = 100, message = "Email must not exceed 100 characters")
    String email,

    @Size(max = 255, message = "Website must not exceed 255 characters")
    String website,

    @Size(max = 500, message = "Logo URL must not exceed 500 characters")
    String logoUrl,

    Long countryId
) {}

