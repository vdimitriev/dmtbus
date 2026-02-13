package mk.dmt.bus.service;

import mk.dmt.bus.dto.BusStationDto;
import mk.dmt.bus.dto.request.CreateBusStationRequest;
import mk.dmt.bus.entity.BusStation;
import mk.dmt.bus.entity.City;
import mk.dmt.bus.exception.ResourceNotFoundException;
import mk.dmt.bus.mapper.EntityMapper;
import mk.dmt.bus.repository.BusStationRepository;
import mk.dmt.bus.repository.CityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BusStationService {

    private final BusStationRepository busStationRepository;
    private final CityRepository cityRepository;
    private final EntityMapper mapper;

    public BusStationService(BusStationRepository busStationRepository, CityRepository cityRepository, EntityMapper mapper) {
        this.busStationRepository = busStationRepository;
        this.cityRepository = cityRepository;
        this.mapper = mapper;
    }

    public List<BusStationDto> getAllStations() {
        return mapper.toBusStationDtoList(busStationRepository.findAll());
    }

    public BusStationDto getStationById(Long id) {
        BusStation station = busStationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bus station not found with id: " + id));
        return mapper.toDto(station);
    }

    public List<BusStationDto> getStationsByCityId(Long cityId) {
        return mapper.toBusStationDtoList(busStationRepository.findByCityId(cityId));
    }

    public List<BusStationDto> getStationsByCountryCode(String countryCode) {
        return mapper.toBusStationDtoList(busStationRepository.findByCountryCode(countryCode));
    }

    public List<BusStationDto> searchStations(String name) {
        return mapper.toBusStationDtoList(busStationRepository.findByNameContainingIgnoreCase(name));
    }

    public List<BusStationDto> getMainStations() {
        return mapper.toBusStationDtoList(busStationRepository.findByIsMainStationTrue());
    }

    public BusStationDto createStation(CreateBusStationRequest request) {
        City city = cityRepository.findById(request.cityId())
            .orElseThrow(() -> new ResourceNotFoundException("City not found with id: " + request.cityId()));

        BusStation station = new BusStation(request.name(), request.nameLocal(), request.address(), city);
        station.setLatitude(request.latitude());
        station.setLongitude(request.longitude());
        station.setPhoneNumber(request.phoneNumber());
        station.setIsMainStation(request.isMainStation() != null ? request.isMainStation() : false);

        station = busStationRepository.save(station);
        return mapper.toDto(station);
    }

    public BusStationDto updateStation(Long id, CreateBusStationRequest request) {
        BusStation station = busStationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bus station not found with id: " + id));

        if (!station.getCity().getId().equals(request.cityId())) {
            City city = cityRepository.findById(request.cityId())
                .orElseThrow(() -> new ResourceNotFoundException("City not found with id: " + request.cityId()));
            station.setCity(city);
        }

        station.setName(request.name());
        station.setNameLocal(request.nameLocal());
        station.setAddress(request.address());
        station.setLatitude(request.latitude());
        station.setLongitude(request.longitude());
        station.setPhoneNumber(request.phoneNumber());
        station.setIsMainStation(request.isMainStation() != null ? request.isMainStation() : false);

        station = busStationRepository.save(station);
        return mapper.toDto(station);
    }

    public void deleteStation(Long id) {
        if (!busStationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bus station not found with id: " + id);
        }
        busStationRepository.deleteById(id);
    }
}

