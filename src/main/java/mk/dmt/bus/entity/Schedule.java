package mk.dmt.bus.entity;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalTime arrivalTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "valid_from")
    private java.time.LocalDate validFrom;

    @Column(name = "valid_until")
    private java.time.LocalDate validUntil;

    @Column(length = 500)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_line_id", nullable = false)
    private BusLine busLine;

    public Schedule() {
    }

    public Schedule(BusLine busLine, LocalTime departureTime, LocalTime arrivalTime, DayOfWeek dayOfWeek) {
        this.busLine = busLine;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.dayOfWeek = dayOfWeek;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public java.time.LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(java.time.LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public java.time.LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(java.time.LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BusLine getBusLine() {
        return busLine;
    }

    public void setBusLine(BusLine busLine) {
        this.busLine = busLine;
    }
}

