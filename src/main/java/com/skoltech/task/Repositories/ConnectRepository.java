package com.skoltech.task.Repositories;

import com.skoltech.task.Models.Entities.Helpers.ObjectToSensor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConnectRepository extends CrudRepository<ObjectToSensor, Integer> {
    @Query(value = "SELECT * FROM sk_db.public.objects_to_sensors CON WHERE CON.object_id = :objectId AND CON.sensor_id = :sensorId", nativeQuery = true)
    Optional<ObjectToSensor> findByObjectAndSensor(@Param("objectId") Integer objectId, @Param("sensorId") Integer sensorId);
}
