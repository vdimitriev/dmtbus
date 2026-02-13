package mk.dmt.bus.service;

import mk.dmt.bus.dto.ScheduleDto;
import mk.dmt.bus.dto.request.CreateScheduleRequest;
import mk.dmt.bus.entity.BusLine;
import mk.dmt.bus.entity.Schedule;
import mk.dmt.bus.exception.ResourceNotFoundException;
import mk.dmt.bus.mapper.EntityMapper;
import mk.dmt.bus.repository.BusLineRepository;
import mk.dmt.bus.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final BusLineRepository busLineRepository;
    private final EntityMapper mapper;

    public ScheduleService(ScheduleRepository scheduleRepository, BusLineRepository busLineRepository, EntityMapper mapper) {
        this.scheduleRepository = scheduleRepository;
        this.busLineRepository = busLineRepository;
        this.mapper = mapper;
    }

    public List<ScheduleDto> getAllSchedules() {
        return mapper.toScheduleDtoList(scheduleRepository.findAll());
    }

    public ScheduleDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + id));
        return mapper.toDto(schedule);
    }

    public List<ScheduleDto> getSchedulesByBusLineId(Long busLineId) {
        return mapper.toScheduleDtoList(scheduleRepository.findByBusLineId(busLineId));
    }

    public List<ScheduleDto> getSchedulesByBusLineIdAndDay(Long busLineId, DayOfWeek dayOfWeek) {
        return mapper.toScheduleDtoList(scheduleRepository.findByBusLineIdAndDayOfWeek(busLineId, dayOfWeek));
    }

    public List<ScheduleDto> getActiveSchedules(Long busLineId, DayOfWeek dayOfWeek, LocalDate date) {
        return mapper.toScheduleDtoList(scheduleRepository.findActiveSchedules(busLineId, dayOfWeek, date));
    }

    public ScheduleDto createSchedule(CreateScheduleRequest request) {
        BusLine busLine = busLineRepository.findById(request.busLineId())
            .orElseThrow(() -> new ResourceNotFoundException("Bus line not found with id: " + request.busLineId()));

        Schedule schedule = new Schedule(busLine, request.departureTime(), request.arrivalTime(), request.dayOfWeek());
        schedule.setValidFrom(request.validFrom());
        schedule.setValidUntil(request.validUntil());
        schedule.setNotes(request.notes());
        schedule.setIsActive(true);

        schedule = scheduleRepository.save(schedule);
        return mapper.toDto(schedule);
    }

    public List<ScheduleDto> createWeeklySchedule(Long busLineId, CreateScheduleRequest baseRequest) {
        BusLine busLine = busLineRepository.findById(busLineId)
            .orElseThrow(() -> new ResourceNotFoundException("Bus line not found with id: " + busLineId));

        List<Schedule> schedules = new java.util.ArrayList<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            Schedule schedule = new Schedule(busLine, baseRequest.departureTime(), baseRequest.arrivalTime(), day);
            schedule.setValidFrom(baseRequest.validFrom());
            schedule.setValidUntil(baseRequest.validUntil());
            schedule.setNotes(baseRequest.notes());
            schedule.setIsActive(true);
            schedules.add(schedule);
        }

        return mapper.toScheduleDtoList(scheduleRepository.saveAll(schedules));
    }

    public ScheduleDto updateSchedule(Long id, CreateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + id));

        if (!schedule.getBusLine().getId().equals(request.busLineId())) {
            BusLine busLine = busLineRepository.findById(request.busLineId())
                .orElseThrow(() -> new ResourceNotFoundException("Bus line not found with id: " + request.busLineId()));
            schedule.setBusLine(busLine);
        }

        schedule.setDepartureTime(request.departureTime());
        schedule.setArrivalTime(request.arrivalTime());
        schedule.setDayOfWeek(request.dayOfWeek());
        schedule.setValidFrom(request.validFrom());
        schedule.setValidUntil(request.validUntil());
        schedule.setNotes(request.notes());

        schedule = scheduleRepository.save(schedule);
        return mapper.toDto(schedule);
    }

    public ScheduleDto toggleScheduleStatus(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + id));
        schedule.setIsActive(!schedule.getIsActive());
        schedule = scheduleRepository.save(schedule);
        return mapper.toDto(schedule);
    }

    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Schedule not found with id: " + id);
        }
        scheduleRepository.deleteById(id);
    }
}

