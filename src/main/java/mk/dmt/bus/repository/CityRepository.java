package mk.dmt.bus.repository;

import mk.dmt.bus.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByCountryId(Long countryId);

    List<City> findByCountryCode(String countryCode);

    List<City> findByNameContainingIgnoreCase(String name);

    @Query("SELECT c FROM City c WHERE c.country.code = :countryCode AND LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<City> findByCountryCodeAndNameContaining(@Param("countryCode") String countryCode, @Param("name") String name);
}

