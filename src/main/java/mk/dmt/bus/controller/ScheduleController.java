package mk.dmt.bus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mk.dmt.bus.dto.ScheduleDto;
import mk.dmt.bus.dto.request.CreateScheduleRequest;
import mk.dmt.bus.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@Tag(name = "Schedules", description = "Bus schedule management endpoints")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    @Operation(summary = "Get all schedules", description = "Retrieve a list of all bus schedules")
    public ResponseEntity<List<ScheduleDto>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get schedule by ID", description = "Retrieve a specific schedule by its ID")
    public ResponseEntity<ScheduleDto> getScheduleById(@Parameter(description = "Schedule ID") @PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

    @GetMapping("/line/{busLineId}")
    @Operation(summary = "Get schedules by bus line", description = "Retrieve all schedules for a specific bus line")
    public ResponseEntity<List<ScheduleDto>> getSchedulesByBusLineId(@Parameter(description = "Bus line ID") @PathVariable Long busLineId) {
        return ResponseEntity.ok(scheduleService.getSchedulesByBusLineId(busLineId));
    }

    @GetMapping("/line/{busLineId}/day/{dayOfWeek}")
    @Operation(summary = "Get schedules by bus line and day", description = "Retrieve schedules for a specific bus line on a particular day of week")
    public ResponseEntity<List<ScheduleDto>> getSchedulesByBusLineIdAndDay(
            @Parameter(description = "Bus line ID") @PathVariable Long busLineId,
            @Parameter(description = "Day of week (MONDAY, TUESDAY, etc.)") @PathVariable DayOfWeek dayOfWeek) {
        return ResponseEntity.ok(scheduleService.getSchedulesByBusLineIdAndDay(busLineId, dayOfWeek));
    }

    @GetMapping("/line/{busLineId}/active")
    @Operation(summary = "Get active schedules", description = "Retrieve active schedules for a specific bus line on a given day and date")
    public ResponseEntity<List<ScheduleDto>> getActiveSchedules(
            @Parameter(description = "Bus line ID") @PathVariable Long busLineId,
            @Parameter(description = "Day of week (MONDAY, TUESDAY, etc.)") @RequestParam DayOfWeek dayOfWeek,
            @Parameter(description = "Date (optional, defaults to today)") @RequestParam(required = false) LocalDate date) {
        LocalDate searchDate = date != null ? date : LocalDate.now();
        return ResponseEntity.ok(scheduleService.getActiveSchedules(busLineId, dayOfWeek, searchDate));
    }

    @PostMapping
    @Operation(summary = "Create a new schedule", description = "Create a new bus schedule in the system")
    public ResponseEntity<ScheduleDto> createSchedule(@Valid @RequestBody CreateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(request));
    }

    @PostMapping("/line/{busLineId}/weekly")
    @Operation(summary = "Create weekly schedules", description = "Create schedules for all days of the week for a bus line")
    public ResponseEntity<List<ScheduleDto>> createWeeklySchedule(
            @Parameter(description = "Bus line ID") @PathVariable Long busLineId,
            @Valid @RequestBody CreateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createWeeklySchedule(busLineId, request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a schedule", description = "Update an existing bus schedule")
    public ResponseEntity<ScheduleDto> updateSchedule(
            @Parameter(description = "Schedule ID") @PathVariable Long id,
            @Valid @RequestBody CreateScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, request));
    }

    @PatchMapping("/{id}/toggle-status")
    @Operation(summary = "Toggle schedule status", description = "Toggle the active status of a schedule")
    public ResponseEntity<ScheduleDto> toggleScheduleStatus(@Parameter(description = "Schedule ID") @PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.toggleScheduleStatus(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a schedule", description = "Delete a bus schedule from the system")
    public ResponseEntity<Void> deleteSchedule(@Parameter(description = "Schedule ID") @PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}

