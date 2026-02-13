package mk.dmt.bus.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bus_stations")
public class BusStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "name_local", length = 150)
    private String nameLocal;

    @Column(length = 255)
    private String address;

    @Column(precision = 10)
    private Double latitude;

    @Column(precision = 10)
    private Double longitude;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "is_main_station")
    private Boolean isMainStation = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    public BusStation() {
    }

    public BusStation(String name, String nameLocal, String address, City city) {
        this.name = name;
        this.nameLocal = nameLocal;
        this.address = address;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameLocal() {
        return nameLocal;
    }

    public void setNameLocal(String nameLocal) {
        this.nameLocal = nameLocal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getIsMainStation() {
        return isMainStation;
    }

    public void setIsMainStation(Boolean isMainStation) {
        this.isMainStation = isMainStation;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}

