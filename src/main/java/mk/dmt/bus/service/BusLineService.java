package mk.dmt.bus.service;

import mk.dmt.bus.dto.BusLineDto;
import mk.dmt.bus.dto.request.CreateBusLineRequest;
import mk.dmt.bus.dto.request.CreateBusLineStopRequest;
import mk.dmt.bus.entity.*;
import mk.dmt.bus.exception.ResourceNotFoundException;
import mk.dmt.bus.mapper.EntityMapper;
import mk.dmt.bus.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BusLineService {

    private final BusLineRepository busLineRepository;
    private final BusCompanyRepository busCompanyRepository;
    private final BusStationRepository busStationRepository;
    private final BusLineStopRepository busLineStopRepository;
    private final EntityMapper mapper;

    public BusLineService(BusLineRepository busLineRepository,
                          BusCompanyRepository busCompanyRepository,
                          BusStationRepository busStationRepository,
                          BusLineStopRepository busLineStopRepository,
                          EntityMapper mapper) {
        this.busLineRepository = busLineRepository;
        this.busCompanyRepository = busCompanyRepository;
        this.busStationRepository = busStationRepository;
        this.busLineStopRepository = busLineStopRepository;
        this.mapper = mapper;
    }

    public List<BusLineDto> getAllBusLines() {
        return mapper.toBusLineDtoList(busLineRepository.findAll());
    }

    public BusLineDto getBusLineById(Long id) {
        BusLine busLine = busLineRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bus line not found with id: " + id));
        return mapper.toDto(busLine);
    }

    public List<BusLineDto> getActiveBusLines() {
        return mapper.toBusLineDtoList(busLineRepository.findByIsActiveTrue());
    }

    public List<BusLineDto> getInternationalLines() {
        return mapper.toBusLineDtoList(busLineRepository.findByIsInternationalTrue());
    }

    public List<BusLineDto> getDomesticLines() {
        return mapper.toBusLineDtoList(busLineRepository.findByIsInternationalFalse());
    }

    public List<BusLineDto> getBusLinesByCompanyId(Long companyId) {
        return mapper.toBusLineDtoList(busLineRepository.findByBusCompanyId(companyId));
    }

    public List<BusLineDto> getBusLinesByCityId(Long cityId) {
        return mapper.toBusLineDtoList(busLineRepository.findByCityId(cityId));
    }

    public List<BusLineDto> getBusLinesByCountryCode(String countryCode) {
        return mapper.toBusLineDtoList(busLineRepository.findByCountryCode(countryCode));
    }

    public List<BusLineDto> searchRoutes(Long originCityId, Long destinationCityId) {
        return mapper.toBusLineDtoList(
            busLineRepository.findByOriginAndDestinationCities(originCityId, destinationCityId));
    }

    public BusLineDto createBusLine(CreateBusLineRequest request) {
        BusCompany company = busCompanyRepository.findById(request.busCompanyId())
            .orElseThrow(() -> new ResourceNotFoundException("Bus company not found with id: " + request.busCompanyId()));

        BusStation originStation = busStationRepository.findById(request.originStationId())
            .orElseThrow(() -> new ResourceNotFoundException("Origin station not found with id: " + request.originStationId()));

        BusStation destinationStation = busStationRepository.findById(request.destinationStationId())
            .orElseThrow(() -> new ResourceNotFoundException("Destination station not found with id: " + request.destinationStationId()));

        BusLine busLine = new BusLine(request.lineNumber(), request.name(), company, originStation, destinationStation);
        busLine.setDescription(request.description());
        busLine.setIsInternational(request.isInternational() != null ? request.isInternational() : false);
        busLine.setDistanceKm(request.distanceKm());
        busLine.setEstimatedDurationMinutes(request.estimatedDurationMinutes());
        busLine.setIsActive(true);

        busLine = busLineRepository.save(busLine);

        // Add intermediate stops if provided
        if (request.stops() != null && !request.stops().isEmpty()) {
            for (CreateBusLineStopRequest stopRequest : request.stops()) {
                addStopToBusLine(busLine, stopRequest);
            }
        }

        return mapper.toDto(busLine);
    }

    private void addStopToBusLine(BusLine busLine, CreateBusLineStopRequest stopRequest) {
        BusStation station = busStationRepository.findById(stopRequest.busStationId())
            .orElseThrow(() -> new ResourceNotFoundException("Bus station not found with id: " + stopRequest.busStationId()));

        BusLineStop stop = new BusLineStop(busLine, station, stopRequest.stopOrder());
        stop.setArrivalOffsetMinutes(stopRequest.arrivalOffsetMinutes());
        stop.setDepartureOffsetMinutes(stopRequest.departureOffsetMinutes());
        stop.setDistanceFromOriginKm(stopRequest.distanceFromOriginKm());

        busLine.getStops().add(stop);
    }

    public BusLineDto updateBusLine(Long id, CreateBusLineRequest request) {
        BusLine busLine = busLineRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bus line not found with id: " + id));

        if (!busLine.getBusCompany().getId().equals(request.busCompanyId())) {
            BusCompany company = busCompanyRepository.findById(request.busCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Bus company not found with id: " + request.busCompanyId()));
            busLine.setBusCompany(company);
        }

        if (!busLine.getOriginStation().getId().equals(request.originStationId())) {
            BusStation originStation = busStationRepository.findById(request.originStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Origin station not found with id: " + request.originStationId()));
            busLine.setOriginStation(originStation);
        }

        if (!busLine.getDestinationStation().getId().equals(request.destinationStationId())) {
            BusStation destinationStation = busStationRepository.findById(request.destinationStationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination station not found with id: " + request.destinationStationId()));
            busLine.setDestinationStation(destinationStation);
        }

        busLine.setLineNumber(request.lineNumber());
        busLine.setName(request.name());
        busLine.setDescription(request.description());
        busLine.setIsInternational(request.isInternational() != null ? request.isInternational() : false);
        busLine.setDistanceKm(request.distanceKm());
        busLine.setEstimatedDurationMinutes(request.estimatedDurationMinutes());

        // Update stops
        if (request.stops() != null) {
            busLine.getStops().clear();
            for (CreateBusLineStopRequest stopRequest : request.stops()) {
                addStopToBusLine(busLine, stopRequest);
            }
        }

        busLine = busLineRepository.save(busLine);
        return mapper.toDto(busLine);
    }

    public BusLineDto toggleBusLineStatus(Long id) {
        BusLine busLine = busLineRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bus line not found with id: " + id));
        busLine.setIsActive(!busLine.getIsActive());
        busLine = busLineRepository.save(busLine);
        return mapper.toDto(busLine);
    }

    public void deleteBusLine(Long id) {
        if (!busLineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bus line not found with id: " + id);
        }
        busLineRepository.deleteById(id);
    }
}

