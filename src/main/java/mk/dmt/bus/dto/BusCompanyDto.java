package mk.dmt.bus.dto;

public record BusCompanyDto(
    Long id,
    String name,
    String registrationNumber,
    String address,
    String phoneNumber,
    String email,
    String website,
    String logoUrl,
    Boolean isActive,
    Long countryId,
    String countryName
) {}

