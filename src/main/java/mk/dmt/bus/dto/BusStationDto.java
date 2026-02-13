package mk.dmt.bus.dto;

public record BusStationDto(
    Long id,
    String name,
    String nameLocal,
    String address,
    Double latitude,
    Double longitude,
    String phoneNumber,
    Boolean isMainStation,
    Long cityId,
    String cityName,
    String countryName
) {}

