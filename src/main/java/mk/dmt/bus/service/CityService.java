package mk.dmt.bus.service;

import mk.dmt.bus.dto.CityDto;
import mk.dmt.bus.dto.request.CreateCityRequest;
import mk.dmt.bus.entity.City;
import mk.dmt.bus.entity.Country;
import mk.dmt.bus.exception.ResourceNotFoundException;
import mk.dmt.bus.mapper.EntityMapper;
import mk.dmt.bus.repository.CityRepository;
import mk.dmt.bus.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CityService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final EntityMapper mapper;

    public CityService(CityRepository cityRepository, CountryRepository countryRepository, EntityMapper mapper) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.mapper = mapper;
    }

    public List<CityDto> getAllCities() {
        return mapper.toCityDtoList(cityRepository.findAll());
    }

    public CityDto getCityById(Long id) {
        City city = cityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("City not found with id: " + id));
        return mapper.toDto(city);
    }

    public List<CityDto> getCitiesByCountryId(Long countryId) {
        return mapper.toCityDtoList(cityRepository.findByCountryId(countryId));
    }

    public List<CityDto> getCitiesByCountryCode(String countryCode) {
        return mapper.toCityDtoList(cityRepository.findByCountryCode(countryCode));
    }

    public List<CityDto> searchCities(String name) {
        return mapper.toCityDtoList(cityRepository.findByNameContainingIgnoreCase(name));
    }

    public List<CityDto> searchCitiesInCountry(String countryCode, String name) {
        return mapper.toCityDtoList(cityRepository.findByCountryCodeAndNameContaining(countryCode, name));
    }

    public CityDto createCity(CreateCityRequest request) {
        Country country = countryRepository.findById(request.countryId())
            .orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + request.countryId()));

        City city = new City(request.name(), request.nameLocal(), request.postalCode(), country);
        city.setLatitude(request.latitude());
        city.setLongitude(request.longitude());

        city = cityRepository.save(city);
        return mapper.toDto(city);
    }

    public CityDto updateCity(Long id, CreateCityRequest request) {
        City city = cityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("City not found with id: " + id));

        if (!city.getCountry().getId().equals(request.countryId())) {
            Country country = countryRepository.findById(request.countryId())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + request.countryId()));
            city.setCountry(country);
        }

        city.setName(request.name());
        city.setNameLocal(request.nameLocal());
        city.setPostalCode(request.postalCode());
        city.setLatitude(request.latitude());
        city.setLongitude(request.longitude());

        city = cityRepository.save(city);
        return mapper.toDto(city);
    }

    public void deleteCity(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new ResourceNotFoundException("City not found with id: " + id);
        }
        cityRepository.deleteById(id);
    }
}

