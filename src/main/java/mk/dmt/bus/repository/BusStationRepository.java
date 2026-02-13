package mk.dmt.bus.repository;

import mk.dmt.bus.entity.BusStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusStationRepository extends JpaRepository<BusStation, Long> {

    List<BusStation> findByCityId(Long cityId);

    List<BusStation> findByNameContainingIgnoreCase(String name);

    @Query("SELECT bs FROM BusStation bs WHERE bs.city.country.id = :countryId")
    List<BusStation> findByCountryId(@Param("countryId") Long countryId);

    @Query("SELECT bs FROM BusStation bs WHERE bs.city.country.code = :countryCode")
    List<BusStation> findByCountryCode(@Param("countryCode") String countryCode);

    List<BusStation> findByIsMainStationTrue();

    @Query("SELECT bs FROM BusStation bs WHERE bs.city.id = :cityId AND bs.isMainStation = true")
    List<BusStation> findMainStationByCityId(@Param("cityId") Long cityId);
}

