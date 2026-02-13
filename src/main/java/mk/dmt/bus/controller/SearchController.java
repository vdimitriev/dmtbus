package mk.dmt.bus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.dmt.bus.dto.SearchResultDto;
import mk.dmt.bus.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@Tag(name = "Search", description = "Bus connection search endpoints")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    @Operation(summary = "Search bus connections by date",
               description = "Search for available bus connections between two cities on a specific date. If no date is provided, defaults to today.")
    public ResponseEntity<List<SearchResultDto>> searchConnections(
            @Parameter(description = "Origin city ID") @RequestParam Long originCityId,
            @Parameter(description = "Destination city ID") @RequestParam Long destinationCityId,
            @Parameter(description = "Travel date (optional, defaults to today)") @RequestParam(required = false) LocalDate date) {
        LocalDate searchDate = date != null ? date : LocalDate.now();
        return ResponseEntity.ok(searchService.searchConnections(originCityId, destinationCityId, searchDate));
    }

    @GetMapping("/day")
    @Operation(summary = "Search bus connections by day of week",
               description = "Search for available bus connections between two cities on a specific day of week.")
    public ResponseEntity<List<SearchResultDto>> searchConnectionsByDay(
            @Parameter(description = "Origin city ID") @RequestParam Long originCityId,
            @Parameter(description = "Destination city ID") @RequestParam Long destinationCityId,
            @Parameter(description = "Day of week (MONDAY, TUESDAY, etc.)") @RequestParam DayOfWeek dayOfWeek) {
        return ResponseEntity.ok(searchService.searchConnectionsByDay(originCityId, destinationCityId, dayOfWeek));
    }
}

