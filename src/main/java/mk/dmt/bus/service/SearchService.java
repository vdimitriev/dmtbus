package mk.dmt.bus.service;

import mk.dmt.bus.dto.BusLineStopDto;
import mk.dmt.bus.dto.SearchResultDto;
import mk.dmt.bus.entity.BusLine;
import mk.dmt.bus.entity.Schedule;
import mk.dmt.bus.mapper.EntityMapper;
import mk.dmt.bus.repository.BusLineRepository;
import mk.dmt.bus.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SearchService {

    private final BusLineRepository busLineRepository;
    private final ScheduleRepository scheduleRepository;
    private final EntityMapper mapper;

    public SearchService(BusLineRepository busLineRepository, ScheduleRepository scheduleRepository, EntityMapper mapper) {
        this.busLineRepository = busLineRepository;
        this.scheduleRepository = scheduleRepository;
        this.mapper = mapper;
    }

    public List<SearchResultDto> searchConnections(Long originCityId, Long destinationCityId, LocalDate travelDate) {
        DayOfWeek dayOfWeek = travelDate != null ? travelDate.getDayOfWeek() : LocalDate.now().getDayOfWeek();

        // Find direct routes
        List<BusLine> directRoutes = busLineRepository.findByOriginAndDestinationCities(originCityId, destinationCityId);

        List<SearchResultDto> results = new ArrayList<>();

        for (BusLine busLine : directRoutes) {
            if (!busLine.getIsActive()) continue;

            List<Schedule> schedules = scheduleRepository.findByBusLineIdAndDayOfWeek(busLine.getId(), dayOfWeek);

            for (Schedule schedule : schedules) {
                if (!schedule.getIsActive()) continue;

                // Check validity period
                if (travelDate != null) {
                    if (schedule.getValidFrom() != null && travelDate.isBefore(schedule.getValidFrom())) continue;
                    if (schedule.getValidUntil() != null && travelDate.isAfter(schedule.getValidUntil())) continue;
                }

                List<BusLineStopDto> stops = busLine.getStops().stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());

                SearchResultDto result = new SearchResultDto(
                    busLine.getId(),
                    busLine.getName(),
                    busLine.getBusCompany().getName(),
                    busLine.getOriginStation().getCity().getName(),
                    busLine.getDestinationStation().getCity().getName(),
                    schedule.getDepartureTime(),
                    schedule.getArrivalTime(),
                    schedule.getDayOfWeek(),
                    busLine.getEstimatedDurationMinutes(),
                    busLine.getDistanceKm(),
                    busLine.getIsInternational(),
                    stops
                );

                results.add(result);
            }
        }

        // Sort by departure time
        results.sort((a, b) -> a.departureTime().compareTo(b.departureTime()));

        return results;
    }

    public List<SearchResultDto> searchConnectionsByDay(Long originCityId, Long destinationCityId, DayOfWeek dayOfWeek) {
        List<BusLine> directRoutes = busLineRepository.findByOriginAndDestinationCities(originCityId, destinationCityId);

        List<SearchResultDto> results = new ArrayList<>();

        for (BusLine busLine : directRoutes) {
            if (!busLine.getIsActive()) continue;

            List<Schedule> schedules = scheduleRepository.findByBusLineIdAndDayOfWeek(busLine.getId(), dayOfWeek);

            for (Schedule schedule : schedules) {
                if (!schedule.getIsActive()) continue;

                List<BusLineStopDto> stops = busLine.getStops().stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());

                SearchResultDto result = new SearchResultDto(
                    busLine.getId(),
                    busLine.getName(),
                    busLine.getBusCompany().getName(),
                    busLine.getOriginStation().getCity().getName(),
                    busLine.getDestinationStation().getCity().getName(),
                    schedule.getDepartureTime(),
                    schedule.getArrivalTime(),
                    schedule.getDayOfWeek(),
                    busLine.getEstimatedDurationMinutes(),
                    busLine.getDistanceKm(),
                    busLine.getIsInternational(),
                    stops
                );

                results.add(result);
            }
        }

        results.sort((a, b) -> a.departureTime().compareTo(b.departureTime()));

        return results;
    }
}

