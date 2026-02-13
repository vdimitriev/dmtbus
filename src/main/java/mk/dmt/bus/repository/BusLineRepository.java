package mk.dmt.bus.repository;

import mk.dmt.bus.entity.BusLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusLineRepository extends JpaRepository<BusLine, Long> {

    List<BusLine> findByBusCompanyId(Long busCompanyId);

    List<BusLine> findByIsActiveTrue();

    List<BusLine> findByIsInternationalTrue();

    List<BusLine> findByIsInternationalFalse();

    @Query("SELECT bl FROM BusLine bl WHERE bl.originStation.id = :stationId OR bl.destinationStation.id = :stationId")
    List<BusLine> findByStationId(@Param("stationId") Long stationId);

    @Query("SELECT bl FROM BusLine bl WHERE bl.originStation.city.id = :cityId OR bl.destinationStation.city.id = :cityId")
    List<BusLine> findByCityId(@Param("cityId") Long cityId);

    @Query("SELECT bl FROM BusLine bl WHERE " +
           "(bl.originStation.city.id = :originCityId AND bl.destinationStation.city.id = :destinationCityId) OR " +
           "(bl.originStation.city.id = :destinationCityId AND bl.destinationStation.city.id = :originCityId)")
    List<BusLine> findByOriginAndDestinationCities(@Param("originCityId") Long originCityId,
                                                    @Param("destinationCityId") Long destinationCityId);

    @Query("SELECT DISTINCT bl FROM BusLine bl " +
           "LEFT JOIN bl.stops s " +
           "WHERE bl.originStation.city.id = :originCityId " +
           "OR bl.destinationStation.city.id = :originCityId " +
           "OR s.busStation.city.id = :originCityId")
    List<BusLine> findLinesPassingThroughCity(@Param("originCityId") Long cityId);

    @Query("SELECT bl FROM BusLine bl WHERE bl.originStation.city.country.code = :countryCode " +
           "OR bl.destinationStation.city.country.code = :countryCode")
    List<BusLine> findByCountryCode(@Param("countryCode") String countryCode);
}

