package mk.dmt.bus.repository;

import mk.dmt.bus.entity.BusLineStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusLineStopRepository extends JpaRepository<BusLineStop, Long> {

    List<BusLineStop> findByBusLineIdOrderByStopOrderAsc(Long busLineId);

    List<BusLineStop> findByBusStationId(Long busStationId);

    void deleteByBusLineId(Long busLineId);
}

