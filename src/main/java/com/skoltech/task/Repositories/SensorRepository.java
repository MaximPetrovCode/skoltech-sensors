package com.skoltech.task.Repositories;

import com.skoltech.task.Models.Entities.SensorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepository extends CrudRepository<SensorEntity, Integer> {
    @Query(value = "SELECT * FROM sk_db.public.sensor SEN WHERE SEN.id = :sensorId",nativeQuery = true)
    Optional<SensorEntity> findByIndex(Integer sensorId);
}
