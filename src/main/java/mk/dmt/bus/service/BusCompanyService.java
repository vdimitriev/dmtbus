package mk.dmt.bus.service;

import mk.dmt.bus.dto.BusCompanyDto;
import mk.dmt.bus.dto.request.CreateBusCompanyRequest;
import mk.dmt.bus.entity.BusCompany;
import mk.dmt.bus.entity.Country;
import mk.dmt.bus.exception.ResourceNotFoundException;
import mk.dmt.bus.mapper.EntityMapper;
import mk.dmt.bus.repository.BusCompanyRepository;
import mk.dmt.bus.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BusCompanyService {

    private final BusCompanyRepository busCompanyRepository;
    private final CountryRepository countryRepository;
    private final EntityMapper mapper;

    public BusCompanyService(BusCompanyRepository busCompanyRepository, CountryRepository countryRepository, EntityMapper mapper) {
        this.busCompanyRepository = busCompanyRepository;
        this.countryRepository = countryRepository;
        this.mapper = mapper;
    }

    public List<BusCompanyDto> getAllCompanies() {
        return mapper.toBusCompanyDtoList(busCompanyRepository.findAll());
    }

    public BusCompanyDto getCompanyById(Long id) {
        BusCompany company = busCompanyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bus company not found with id: " + id));
        return mapper.toDto(company);
    }

    public List<BusCompanyDto> getCompaniesByCountryCode(String countryCode) {
        return mapper.toBusCompanyDtoList(busCompanyRepository.findByCountryCode(countryCode));
    }

    public List<BusCompanyDto> getActiveCompanies() {
        return mapper.toBusCompanyDtoList(busCompanyRepository.findByIsActiveTrue());
    }

    public List<BusCompanyDto> searchCompanies(String name) {
        return mapper.toBusCompanyDtoList(busCompanyRepository.findByNameContainingIgnoreCase(name));
    }

    public BusCompanyDto createCompany(CreateBusCompanyRequest request) {
        Country country = null;
        if (request.countryId() != null) {
            country = countryRepository.findById(request.countryId())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + request.countryId()));
        }

        BusCompany company = new BusCompany(request.name(), request.registrationNumber(), country);
        company.setAddress(request.address());
        company.setPhoneNumber(request.phoneNumber());
        company.setEmail(request.email());
        company.setWebsite(request.website());
        company.setLogoUrl(request.logoUrl());
        company.setIsActive(true);

        company = busCompanyRepository.save(company);
        return mapper.toDto(company);
    }

    public BusCompanyDto updateCompany(Long id, CreateBusCompanyRequest request) {
        BusCompany company = busCompanyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bus company not found with id: " + id));

        if (request.countryId() != null) {
            Country country = countryRepository.findById(request.countryId())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + request.countryId()));
            company.setCountry(country);
        }

        company.setName(request.name());
        company.setRegistrationNumber(request.registrationNumber());
        company.setAddress(request.address());
        company.setPhoneNumber(request.phoneNumber());
        company.setEmail(request.email());
        company.setWebsite(request.website());
        company.setLogoUrl(request.logoUrl());

        company = busCompanyRepository.save(company);
        return mapper.toDto(company);
    }

    public BusCompanyDto toggleCompanyStatus(Long id) {
        BusCompany company = busCompanyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bus company not found with id: " + id));
        company.setIsActive(!company.getIsActive());
        company = busCompanyRepository.save(company);
        return mapper.toDto(company);
    }

    public void deleteCompany(Long id) {
        if (!busCompanyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bus company not found with id: " + id);
        }
        busCompanyRepository.deleteById(id);
    }
}

