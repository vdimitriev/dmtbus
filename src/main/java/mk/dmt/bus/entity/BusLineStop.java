package mk.dmt.bus.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bus_line_stops")
public class BusLineStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stop_order", nullable = false)
    private Integer stopOrder;

    @Column(name = "arrival_offset_minutes")
    private Integer arrivalOffsetMinutes;

    @Column(name = "departure_offset_minutes")
    private Integer departureOffsetMinutes;

    @Column(name = "distance_from_origin_km")
    private Integer distanceFromOriginKm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_line_id", nullable = false)
    private BusLine busLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_station_id", nullable = false)
    private BusStation busStation;

    public BusLineStop() {
    }

    public BusLineStop(BusLine busLine, BusStation busStation, Integer stopOrder) {
        this.busLine = busLine;
        this.busStation = busStation;
        this.stopOrder = stopOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(Integer stopOrder) {
        this.stopOrder = stopOrder;
    }

    public Integer getArrivalOffsetMinutes() {
        return arrivalOffsetMinutes;
    }

    public void setArrivalOffsetMinutes(Integer arrivalOffsetMinutes) {
        this.arrivalOffsetMinutes = arrivalOffsetMinutes;
    }

    public Integer getDepartureOffsetMinutes() {
        return departureOffsetMinutes;
    }

    public void setDepartureOffsetMinutes(Integer departureOffsetMinutes) {
        this.departureOffsetMinutes = departureOffsetMinutes;
    }

    public Integer getDistanceFromOriginKm() {
        return distanceFromOriginKm;
    }

    public void setDistanceFromOriginKm(Integer distanceFromOriginKm) {
        this.distanceFromOriginKm = distanceFromOriginKm;
    }

    public BusLine getBusLine() {
        return busLine;
    }

    public void setBusLine(BusLine busLine) {
        this.busLine = busLine;
    }

    public BusStation getBusStation() {
        return busStation;
    }

    public void setBusStation(BusStation busStation) {
        this.busStation = busStation;
    }
}

