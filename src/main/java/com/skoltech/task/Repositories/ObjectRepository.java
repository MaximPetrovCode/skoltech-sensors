package com.skoltech.task.Repositories;

import com.skoltech.task.Models.Entities.ObjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectRepository extends CrudRepository<ObjectEntity, Integer> {
}
