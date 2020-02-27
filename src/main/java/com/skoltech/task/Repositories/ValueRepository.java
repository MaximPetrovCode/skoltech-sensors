package com.skoltech.task.Repositories;

import com.skoltech.task.Models.Entities.Helpers.AvgValue;
import com.skoltech.task.Models.Entities.ValueEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ValueRepository extends CrudRepository<ValueEntity, Integer> {
    @Query(value = "SELECT id, AGGR.object_id, AGGR.sensor_id, RESULT.value, AGGR.created_at " +
            "FROM (select VAL.object_id, VAL.sensor_id, max(VAL.created_at) as created_at " +
            "      from sk_db.public.value VAL " +
            "      WHERE VAL.object_id = :ID " +
            "      group by VAL.sensor_id, VAL.object_id) AGGR " +
            "JOIN sk_db.public.value RESULT ON " +
            "RESULT.object_id = AGGR.object_id AND " +
            "RESULT.sensor_id = AGGR.sensor_id AND " +
            "RESULT.created_at = AGGR.created_at", nativeQuery = true)
    Optional<List<ValueEntity>> findLatestUsingGroupBy(@Param("ID") Integer id);

    @Query(value = "SELECT " +
            "       VAL1.id, " +
            "       OTS.object_id, " +
            "       OTS.sensor_id, " +
            "       VAL1.created_at, " +
            "       VAL1.value " +
            "FROM sk_db.public.objects_to_sensors OTS " +
            "         LEFT JOIN sk_db.public.value VAL1 ON VAL1.id = ( " +
            "    SELECT VAL.id " +
            "    FROM sk_db.public.value VAL " +
            "    WHERE VAL.object_id = OTS.object_id " +
            "      AND VAL.sensor_id = OTS.sensor_id " +
            "    ORDER BY VAL.created_at DESC " +
            "    LIMIT 1) WHERE OTS.object_id = :objectId", nativeQuery = true)
    Optional<List<ValueEntity>> findLatest(@Param("objectId") Integer objectId);

    @Query(value = "SELECT " +
            "       OTS.object_id as objectId, " +
            "       avg(( " +
            "           SELECT VAL.value " +
            "           FROM sk_db.public.value VAL " +
            "           WHERE VAL.object_id = OTS.object_id " +
            "             AND VAL.sensor_id = OTS.sensor_id " +
            "           ORDER BY VAL.created_at DESC " +
            "           LIMIT 1)) AS value " +
            "FROM sk_db.public.objects_to_sensors OTS " +
            "GROUP BY OTS.object_id ;", nativeQuery = true)
    List<AvgValue> getAvg();

    @Query(value = "SELECT * FROM sk_db.public.value WHERE sensor_id = :sensorId AND :FROM <= created_at AND created_at <= :TO", nativeQuery = true)
    Optional<List<ValueEntity>> findByInterval(@Param("sensorId") Integer sensorId, @Param("FROM") Long from, @Param("TO") Long to);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sk_db.public.value x USING sk_db.public.value y WHERE x.id > y.id AND " +
            "x.created_at = y.created_at AND " +
            "x.object_id = y.object_id AND " +
            "x.value = y.value AND " +
            "x.sensor_id = y.sensor_id",
            nativeQuery = true)
    void deleteDuplicates();

    @Query(value = "SELECT count(*) FROM sk_db.public.value", nativeQuery = true)
    Optional<Integer> countAll();
}

