package mk.dmt.bus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mk.dmt.bus.dto.CityDto;
import mk.dmt.bus.dto.request.CreateCityRequest;
import mk.dmt.bus.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@Tag(name = "Cities", description = "City management endpoints")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    @Operation(summary = "Get all cities", description = "Retrieve a list of all cities in the system")
    public ResponseEntity<List<CityDto>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get city by ID", description = "Retrieve a specific city by its ID")
    public ResponseEntity<CityDto> getCityById(@Parameter(description = "City ID") @PathVariable Long id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }

    @GetMapping("/country/{countryId}")
    @Operation(summary = "Get cities by country ID", description = "Retrieve all cities in a specific country")
    public ResponseEntity<List<CityDto>> getCitiesByCountryId(@Parameter(description = "Country ID") @PathVariable Long countryId) {
        return ResponseEntity.ok(cityService.getCitiesByCountryId(countryId));
    }

    @GetMapping("/country/code/{countryCode}")
    @Operation(summary = "Get cities by country code", description = "Retrieve all cities in a country using country code (e.g., MK, RS, BG)")
    public ResponseEntity<List<CityDto>> getCitiesByCountryCode(@Parameter(description = "Country code (e.g., MK, RS, BG)") @PathVariable String countryCode) {
        return ResponseEntity.ok(cityService.getCitiesByCountryCode(countryCode));
    }

    @GetMapping("/search")
    @Operation(summary = "Search cities by name", description = "Search for cities matching the provided name")
    public ResponseEntity<List<CityDto>> searchCities(@Parameter(description = "City name to search for") @RequestParam String name) {
        return ResponseEntity.ok(cityService.searchCities(name));
    }

    @GetMapping("/search/{countryCode}")
    @Operation(summary = "Search cities by name in a country", description = "Search for cities in a specific country matching the provided name")
    public ResponseEntity<List<CityDto>> searchCitiesInCountry(
            @Parameter(description = "Country code (e.g., MK, RS, BG)") @PathVariable String countryCode,
            @Parameter(description = "City name to search for") @RequestParam String name) {
        return ResponseEntity.ok(cityService.searchCitiesInCountry(countryCode, name));
    }

    @PostMapping
    @Operation(summary = "Create a new city", description = "Create a new city in the system")
    public ResponseEntity<CityDto> createCity(@Valid @RequestBody CreateCityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.createCity(request));
    }

    // ...existing code...
    public ResponseEntity<CityDto> updateCity(@PathVariable Long id, @Valid @RequestBody CreateCityRequest request) {
        return ResponseEntity.ok(cityService.updateCity(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}

