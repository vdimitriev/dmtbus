package mk.dmt.bus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mk.dmt.bus.dto.BusLineDto;
import mk.dmt.bus.dto.request.CreateBusLineRequest;
import mk.dmt.bus.service.BusLineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lines")
@Tag(name = "Bus Lines", description = "Bus line management endpoints")
public class BusLineController {

    private final BusLineService busLineService;

    public BusLineController(BusLineService busLineService) {
        this.busLineService = busLineService;
    }

    @GetMapping
    @Operation(summary = "Get all bus lines", description = "Retrieve a list of all bus lines")
    public ResponseEntity<List<BusLineDto>> getAllBusLines() {
        return ResponseEntity.ok(busLineService.getAllBusLines());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get bus line by ID", description = "Retrieve a specific bus line by its ID")
    public ResponseEntity<BusLineDto> getBusLineById(@Parameter(description = "Bus line ID") @PathVariable Long id) {
        return ResponseEntity.ok(busLineService.getBusLineById(id));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active bus lines", description = "Retrieve all active bus lines")
    public ResponseEntity<List<BusLineDto>> getActiveBusLines() {
        return ResponseEntity.ok(busLineService.getActiveBusLines());
    }

    @GetMapping("/international")
    @Operation(summary = "Get international lines", description = "Retrieve all international bus lines")
    public ResponseEntity<List<BusLineDto>> getInternationalLines() {
        return ResponseEntity.ok(busLineService.getInternationalLines());
    }

    @GetMapping("/domestic")
    @Operation(summary = "Get domestic lines", description = "Retrieve all domestic bus lines")
    public ResponseEntity<List<BusLineDto>> getDomesticLines() {
        return ResponseEntity.ok(busLineService.getDomesticLines());
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get lines by company", description = "Retrieve all bus lines operated by a specific company")
    public ResponseEntity<List<BusLineDto>> getBusLinesByCompanyId(@Parameter(description = "Bus company ID") @PathVariable Long companyId) {
        return ResponseEntity.ok(busLineService.getBusLinesByCompanyId(companyId));
    }

    @GetMapping("/city/{cityId}")
    @Operation(summary = "Get lines by city", description = "Retrieve all bus lines serving a specific city")
    public ResponseEntity<List<BusLineDto>> getBusLinesByCityId(@Parameter(description = "City ID") @PathVariable Long cityId) {
        return ResponseEntity.ok(busLineService.getBusLinesByCityId(cityId));
    }

    @GetMapping("/country/{countryCode}")
    @Operation(summary = "Get lines by country", description = "Retrieve all bus lines in a specific country")
    public ResponseEntity<List<BusLineDto>> getBusLinesByCountryCode(@Parameter(description = "Country code (e.g., MK, RS, BG)") @PathVariable String countryCode) {
        return ResponseEntity.ok(busLineService.getBusLinesByCountryCode(countryCode));
    }

    @GetMapping("/route")
    @Operation(summary = "Search routes", description = "Search for bus routes between two cities")
    public ResponseEntity<List<BusLineDto>> searchRoutes(
            @Parameter(description = "Origin city ID") @RequestParam Long originCityId,
            @Parameter(description = "Destination city ID") @RequestParam Long destinationCityId) {
        return ResponseEntity.ok(busLineService.searchRoutes(originCityId, destinationCityId));
    }

    @PostMapping
    @Operation(summary = "Create a new bus line", description = "Create a new bus line in the system")
    public ResponseEntity<BusLineDto> createBusLine(@Valid @RequestBody CreateBusLineRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(busLineService.createBusLine(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a bus line", description = "Update an existing bus line")
    public ResponseEntity<BusLineDto> updateBusLine(
            @Parameter(description = "Bus line ID") @PathVariable Long id,
            @Valid @RequestBody CreateBusLineRequest request) {
        return ResponseEntity.ok(busLineService.updateBusLine(id, request));
    }

    @PatchMapping("/{id}/toggle-status")
    @Operation(summary = "Toggle bus line status", description = "Toggle the active status of a bus line")
    public ResponseEntity<BusLineDto> toggleBusLineStatus(@Parameter(description = "Bus line ID") @PathVariable Long id) {
        return ResponseEntity.ok(busLineService.toggleBusLineStatus(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a bus line", description = "Delete a bus line from the system")
    public ResponseEntity<Void> deleteBusLine(@Parameter(description = "Bus line ID") @PathVariable Long id) {
        busLineService.deleteBusLine(id);
        return ResponseEntity.noContent().build();
    }
}

