package mk.dmt.bus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mk.dmt.bus.dto.BusCompanyDto;
import mk.dmt.bus.dto.request.CreateBusCompanyRequest;
import mk.dmt.bus.service.BusCompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@Tag(name = "Bus Companies", description = "Bus company management endpoints")
public class BusCompanyController {

    private final BusCompanyService busCompanyService;

    public BusCompanyController(BusCompanyService busCompanyService) {
        this.busCompanyService = busCompanyService;
    }

    @GetMapping
    @Operation(summary = "Get all companies", description = "Retrieve a list of all bus companies")
    public ResponseEntity<List<BusCompanyDto>> getAllCompanies() {
        return ResponseEntity.ok(busCompanyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get company by ID", description = "Retrieve a specific bus company by its ID")
    public ResponseEntity<BusCompanyDto> getCompanyById(@Parameter(description = "Company ID") @PathVariable Long id) {
        return ResponseEntity.ok(busCompanyService.getCompanyById(id));
    }

    @GetMapping("/country/{countryCode}")
    @Operation(summary = "Get companies by country", description = "Retrieve all bus companies operating in a specific country")
    public ResponseEntity<List<BusCompanyDto>> getCompaniesByCountryCode(@Parameter(description = "Country code (e.g., MK, RS, BG)") @PathVariable String countryCode) {
        return ResponseEntity.ok(busCompanyService.getCompaniesByCountryCode(countryCode));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active companies", description = "Retrieve all active bus companies")
    public ResponseEntity<List<BusCompanyDto>> getActiveCompanies() {
        return ResponseEntity.ok(busCompanyService.getActiveCompanies());
    }

    @GetMapping("/search")
    @Operation(summary = "Search companies by name", description = "Search for bus companies matching the provided name")
    public ResponseEntity<List<BusCompanyDto>> searchCompanies(@Parameter(description = "Company name to search for") @RequestParam String name) {
        return ResponseEntity.ok(busCompanyService.searchCompanies(name));
    }

    @PostMapping
    @Operation(summary = "Create a new company", description = "Create a new bus company in the system")
    public ResponseEntity<BusCompanyDto> createCompany(@Valid @RequestBody CreateBusCompanyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(busCompanyService.createCompany(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a company", description = "Update an existing bus company")
    public ResponseEntity<BusCompanyDto> updateCompany(
            @Parameter(description = "Company ID") @PathVariable Long id,
            @Valid @RequestBody CreateBusCompanyRequest request) {
        return ResponseEntity.ok(busCompanyService.updateCompany(id, request));
    }

    @PatchMapping("/{id}/toggle-status")
    @Operation(summary = "Toggle company status", description = "Toggle the active status of a bus company")
    public ResponseEntity<BusCompanyDto> toggleCompanyStatus(@Parameter(description = "Company ID") @PathVariable Long id) {
        return ResponseEntity.ok(busCompanyService.toggleCompanyStatus(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a company", description = "Delete a bus company from the system")
    public ResponseEntity<Void> deleteCompany(@Parameter(description = "Company ID") @PathVariable Long id) {
        busCompanyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}

