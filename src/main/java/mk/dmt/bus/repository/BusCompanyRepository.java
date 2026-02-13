package mk.dmt.bus.repository;

import mk.dmt.bus.entity.BusCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusCompanyRepository extends JpaRepository<BusCompany, Long> {

    List<BusCompany> findByCountryId(Long countryId);

    List<BusCompany> findByCountryCode(String countryCode);

    List<BusCompany> findByIsActiveTrue();

    List<BusCompany> findByNameContainingIgnoreCase(String name);

    Optional<BusCompany> findByRegistrationNumber(String registrationNumber);
}

