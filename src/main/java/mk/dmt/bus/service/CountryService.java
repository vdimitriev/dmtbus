package mk.dmt.bus.service;

import mk.dmt.bus.dto.CountryDto;
import mk.dmt.bus.dto.request.CreateCountryRequest;
import mk.dmt.bus.entity.Country;
import mk.dmt.bus.exception.ResourceNotFoundException;
import mk.dmt.bus.exception.DuplicateResourceException;
import mk.dmt.bus.mapper.EntityMapper;
import mk.dmt.bus.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CountryService {

    private final CountryRepository countryRepository;
    private final EntityMapper mapper;

    public CountryService(CountryRepository countryRepository, EntityMapper mapper) {
        this.countryRepository = countryRepository;
        this.mapper = mapper;
    }

    public List<CountryDto> getAllCountries() {
        return mapper.toCountryDtoList(countryRepository.findAll());
    }

    public CountryDto getCountryById(Long id) {
        Country country = countryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + id));
        return mapper.toDto(country);
    }

    public CountryDto getCountryByCode(String code) {
        Country country = countryRepository.findByCode(code)
            .orElseThrow(() -> new ResourceNotFoundException("Country not found with code: " + code));
        return mapper.toDto(country);
    }

    public CountryDto createCountry(CreateCountryRequest request) {
        if (countryRepository.existsByCode(request.code())) {
            throw new DuplicateResourceException("Country with code '" + request.code() + "' already exists");
        }

        Country country = new Country(request.code(), request.name(), request.nameLocal());
        country = countryRepository.save(country);
        return mapper.toDto(country);
    }

    public CountryDto updateCountry(Long id, CreateCountryRequest request) {
        Country country = countryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + id));

        country.setCode(request.code());
        country.setName(request.name());
        country.setNameLocal(request.nameLocal());

        country = countryRepository.save(country);
        return mapper.toDto(country);
    }

    public void deleteCountry(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Country not found with id: " + id);
        }
        countryRepository.deleteById(id);
    }
}

