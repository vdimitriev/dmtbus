package mk.dmt.bus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mk.dmt.bus.dto.CountryDto;
import mk.dmt.bus.dto.request.CreateCountryRequest;
import mk.dmt.bus.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@Tag(name = "Countries", description = "Country management endpoints")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    @Operation(summary = "Get all countries", description = "Retrieve a list of all countries in the system")
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get country by ID", description = "Retrieve a specific country by its ID")
    public ResponseEntity<CountryDto> getCountryById(@Parameter(description = "Country ID") @PathVariable Long id) {
        return ResponseEntity.ok(countryService.getCountryById(id));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get country by code", description = "Retrieve a country by its country code (e.g., MK, RS, BG)")
    public ResponseEntity<CountryDto> getCountryByCode(@Parameter(description = "Country code (e.g., MK, RS, BG)") @PathVariable String code) {
        return ResponseEntity.ok(countryService.getCountryByCode(code));
    }

    @PostMapping
    @Operation(summary = "Create a new country", description = "Create a new country in the system")
    public ResponseEntity<CountryDto> createCountry(@Valid @RequestBody CreateCountryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(countryService.createCountry(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a country", description = "Update an existing country")
    public ResponseEntity<CountryDto> updateCountry(
            @Parameter(description = "Country ID") @PathVariable Long id,
            @Valid @RequestBody CreateCountryRequest request) {
        return ResponseEntity.ok(countryService.updateCountry(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a country", description = "Delete a country from the system")
    public ResponseEntity<Void> deleteCountry(@Parameter(description = "Country ID") @PathVariable Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();
    }
}

