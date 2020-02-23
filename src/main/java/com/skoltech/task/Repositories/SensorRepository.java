package com.skoltech.task.Repositories;

import com.skoltech.task.Models.Entities.SensorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends CrudRepository<SensorEntity, Integer> {
}
