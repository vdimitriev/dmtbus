package mk.dmt.bus.mapper;

import mk.dmt.bus.dto.*;
import mk.dmt.bus.entity.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper {

    public CountryDto toDto(Country country) {
        if (country == null) return null;
        return new CountryDto(
            country.getId(),
            country.getCode(),
            country.getName(),
            country.getNameLocal()
        );
    }

    public CityDto toDto(City city) {
        if (city == null) return null;
        Country country = city.getCountry();
        return new CityDto(
            city.getId(),
            city.getName(),
            city.getNameLocal(),
            city.getPostalCode(),
            city.getLatitude(),
            city.getLongitude(),
            country != null ? country.getId() : null,
            country != null ? country.getName() : null,
            country != null ? country.getCode() : null
        );
    }

    public BusStationDto toDto(BusStation station) {
        if (station == null) return null;
        City city = station.getCity();
        return new BusStationDto(
            station.getId(),
            station.getName(),
            station.getNameLocal(),
            station.getAddress(),
            station.getLatitude(),
            station.getLongitude(),
            station.getPhoneNumber(),
            station.getIsMainStation(),
            city != null ? city.getId() : null,
            city != null ? city.getName() : null,
            city != null && city.getCountry() != null ? city.getCountry().getName() : null
        );
    }

    public BusCompanyDto toDto(BusCompany company) {
        if (company == null) return null;
        Country country = company.getCountry();
        return new BusCompanyDto(
            company.getId(),
            company.getName(),
            company.getRegistrationNumber(),
            company.getAddress(),
            company.getPhoneNumber(),
            company.getEmail(),
            company.getWebsite(),
            company.getLogoUrl(),
            company.getIsActive(),
            country != null ? country.getId() : null,
            country != null ? country.getName() : null
        );
    }

    public BusLineDto toDto(BusLine busLine) {
        if (busLine == null) return null;
        BusCompany company = busLine.getBusCompany();
        List<BusLineStopDto> stopDtos = busLine.getStops() != null
            ? busLine.getStops().stream().map(this::toDto).collect(Collectors.toList())
            : Collections.emptyList();

        return new BusLineDto(
            busLine.getId(),
            busLine.getLineNumber(),
            busLine.getName(),
            busLine.getDescription(),
            busLine.getIsInternational(),
            busLine.getIsActive(),
            busLine.getDistanceKm(),
            busLine.getEstimatedDurationMinutes(),
            company != null ? company.getId() : null,
            company != null ? company.getName() : null,
            toDto(busLine.getOriginStation()),
            toDto(busLine.getDestinationStation()),
            stopDtos
        );
    }

    public BusLineStopDto toDto(BusLineStop stop) {
        if (stop == null) return null;
        BusStation station = stop.getBusStation();
        return new BusLineStopDto(
            stop.getId(),
            stop.getStopOrder(),
            stop.getArrivalOffsetMinutes(),
            stop.getDepartureOffsetMinutes(),
            stop.getDistanceFromOriginKm(),
            station != null ? station.getId() : null,
            station != null ? station.getName() : null,
            station != null && station.getCity() != null ? station.getCity().getName() : null
        );
    }

    public ScheduleDto toDto(Schedule schedule) {
        if (schedule == null) return null;
        BusLine busLine = schedule.getBusLine();
        return new ScheduleDto(
            schedule.getId(),
            schedule.getDepartureTime(),
            schedule.getArrivalTime(),
            schedule.getDayOfWeek(),
            schedule.getIsActive(),
            schedule.getValidFrom(),
            schedule.getValidUntil(),
            schedule.getNotes(),
            busLine != null ? busLine.getId() : null,
            busLine != null ? busLine.getName() : null
        );
    }

    public List<CountryDto> toCountryDtoList(List<Country> countries) {
        return countries.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<CityDto> toCityDtoList(List<City> cities) {
        return cities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<BusStationDto> toBusStationDtoList(List<BusStation> stations) {
        return stations.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<BusCompanyDto> toBusCompanyDtoList(List<BusCompany> companies) {
        return companies.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<BusLineDto> toBusLineDtoList(List<BusLine> busLines) {
        return busLines.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ScheduleDto> toScheduleDtoList(List<Schedule> schedules) {
        return schedules.stream().map(this::toDto).collect(Collectors.toList());
    }
}

