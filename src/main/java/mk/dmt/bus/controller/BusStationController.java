package mk.dmt.bus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mk.dmt.bus.dto.BusStationDto;
import mk.dmt.bus.dto.request.CreateBusStationRequest;
import mk.dmt.bus.service.BusStationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@Tag(name = "Bus Stations", description = "Bus station management endpoints")
public class BusStationController {

    private final BusStationService busStationService;

    public BusStationController(BusStationService busStationService) {
        this.busStationService = busStationService;
    }

    @GetMapping
    @Operation(summary = "Get all stations", description = "Retrieve a list of all bus stations")
    public ResponseEntity<List<BusStationDto>> getAllStations() {
        return ResponseEntity.ok(busStationService.getAllStations());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get station by ID", description = "Retrieve a specific bus station by its ID")
    public ResponseEntity<BusStationDto> getStationById(@Parameter(description = "Station ID") @PathVariable Long id) {
        return ResponseEntity.ok(busStationService.getStationById(id));
    }

    @GetMapping("/city/{cityId}")
    @Operation(summary = "Get stations by city", description = "Retrieve all bus stations in a specific city")
    public ResponseEntity<List<BusStationDto>> getStationsByCityId(@Parameter(description = "City ID") @PathVariable Long cityId) {
        return ResponseEntity.ok(busStationService.getStationsByCityId(cityId));
    }

    @GetMapping("/country/{countryCode}")
    @Operation(summary = "Get stations by country", description = "Retrieve all bus stations in a specific country")
    public ResponseEntity<List<BusStationDto>> getStationsByCountryCode(@Parameter(description = "Country code (e.g., MK, RS, BG)") @PathVariable String countryCode) {
        return ResponseEntity.ok(busStationService.getStationsByCountryCode(countryCode));
    }

    @GetMapping("/search")
    @Operation(summary = "Search stations by name", description = "Search for bus stations matching the provided name")
    public ResponseEntity<List<BusStationDto>> searchStations(@Parameter(description = "Station name to search for") @RequestParam String name) {
        return ResponseEntity.ok(busStationService.searchStations(name));
    }

    @GetMapping("/main")
    @Operation(summary = "Get main stations", description = "Retrieve all main (primary) bus stations")
    public ResponseEntity<List<BusStationDto>> getMainStations() {
        return ResponseEntity.ok(busStationService.getMainStations());
    }

    @PostMapping
    @Operation(summary = "Create a new station", description = "Create a new bus station in the system")
    public ResponseEntity<BusStationDto> createStation(@Valid @RequestBody CreateBusStationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(busStationService.createStation(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a station", description = "Update an existing bus station")
    public ResponseEntity<BusStationDto> updateStation(
            @Parameter(description = "Station ID") @PathVariable Long id,
            @Valid @RequestBody CreateBusStationRequest request) {
        return ResponseEntity.ok(busStationService.updateStation(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a station", description = "Delete a bus station from the system")
    public ResponseEntity<Void> deleteStation(@Parameter(description = "Station ID") @PathVariable Long id) {
        busStationService.deleteStation(id);
        return ResponseEntity.noContent().build();
    }
}

