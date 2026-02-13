package mk.dmt.bus.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bus_lines")
public class BusLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "line_number", length = 50)
    private String lineNumber;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name = "is_international")
    private Boolean isInternational = false;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "distance_km")
    private Integer distanceKm;

    @Column(name = "estimated_duration_minutes")
    private Integer estimatedDurationMinutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_company_id", nullable = false)
    private BusCompany busCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_station_id", nullable = false)
    private BusStation originStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_station_id", nullable = false)
    private BusStation destinationStation;

    @OneToMany(mappedBy = "busLine", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("stopOrder ASC")
    private List<BusLineStop> stops = new ArrayList<>();

    @OneToMany(mappedBy = "busLine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    public BusLine() {
    }

    public BusLine(String lineNumber, String name, BusCompany busCompany,
                   BusStation originStation, BusStation destinationStation) {
        this.lineNumber = lineNumber;
        this.name = name;
        this.busCompany = busCompany;
        this.originStation = originStation;
        this.destinationStation = destinationStation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsInternational() {
        return isInternational;
    }

    public void setIsInternational(Boolean isInternational) {
        this.isInternational = isInternational;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Integer distanceKm) {
        this.distanceKm = distanceKm;
    }

    public Integer getEstimatedDurationMinutes() {
        return estimatedDurationMinutes;
    }

    public void setEstimatedDurationMinutes(Integer estimatedDurationMinutes) {
        this.estimatedDurationMinutes = estimatedDurationMinutes;
    }

    public BusCompany getBusCompany() {
        return busCompany;
    }

    public void setBusCompany(BusCompany busCompany) {
        this.busCompany = busCompany;
    }

    public BusStation getOriginStation() {
        return originStation;
    }

    public void setOriginStation(BusStation originStation) {
        this.originStation = originStation;
    }

    public BusStation getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(BusStation destinationStation) {
        this.destinationStation = destinationStation;
    }

    public List<BusLineStop> getStops() {
        return stops;
    }

    public void setStops(List<BusLineStop> stops) {
        this.stops = stops;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}

