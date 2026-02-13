package mk.dmt.bus.repository;

import mk.dmt.bus.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByBusLineId(Long busLineId);

    List<Schedule> findByBusLineIdAndDayOfWeek(Long busLineId, DayOfWeek dayOfWeek);

    List<Schedule> findByBusLineIdAndIsActiveTrue(Long busLineId);

    @Query("SELECT s FROM Schedule s WHERE s.busLine.id = :busLineId " +
           "AND s.dayOfWeek = :dayOfWeek " +
           "AND s.isActive = true " +
           "AND (s.validFrom IS NULL OR s.validFrom <= :date) " +
           "AND (s.validUntil IS NULL OR s.validUntil >= :date)")
    List<Schedule> findActiveSchedules(@Param("busLineId") Long busLineId,
                                       @Param("dayOfWeek") DayOfWeek dayOfWeek,
                                       @Param("date") LocalDate date);

    @Query("SELECT s FROM Schedule s WHERE s.busLine.id IN :busLineIds " +
           "AND s.dayOfWeek = :dayOfWeek " +
           "AND s.isActive = true")
    List<Schedule> findByBusLineIdsAndDayOfWeek(@Param("busLineIds") List<Long> busLineIds,
                                                 @Param("dayOfWeek") DayOfWeek dayOfWeek);
}

