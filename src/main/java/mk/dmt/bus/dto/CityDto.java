package mk.dmt.bus.dto;

public record CityDto(
    Long id,
    String name,
    String nameLocal,
    String postalCode,
    Double latitude,
    Double longitude,
    Long countryId,
    String countryName,
    String countryCode
) {}

