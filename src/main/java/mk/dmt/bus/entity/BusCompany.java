package mk.dmt.bus.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bus_companies")
public class BusCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "registration_number", length = 50)
    private String registrationNumber;

    @Column(length = 255)
    private String address;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String website;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "busCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BusLine> busLines = new HashSet<>();

    public BusCompany() {
    }

    public BusCompany(String name, String registrationNumber, Country country) {
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.country = country;
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

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<BusLine> getBusLines() {
        return busLines;
    }

    public void setBusLines(Set<BusLine> busLines) {
        this.busLines = busLines;
    }
}

