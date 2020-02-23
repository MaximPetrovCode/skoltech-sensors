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
    @Query(value = "SELECT * FROM sk_db.public.value WHERE object_id = :INDEX ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Optional<ValueEntity> findLatest(@Param("INDEX") Integer id);

    @Query(value = "SELECT object_id as objectId, avg(value) as value FROM sk_db.public.value GROUP BY object_id ORDER BY object_id; ", nativeQuery = true)
    List<AvgValue> getAvg();

    @Query(value = "SELECT * FROM sk_db.public.value WHERE object_id = :ID AND :FROM <= created_at AND created_at <= :TO", nativeQuery = true)
    Optional<List<ValueEntity>> findByInterval(@Param("ID") Integer id, @Param("FROM") Long from, @Param("TO") Long to);

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

