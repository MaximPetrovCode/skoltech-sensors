package com.skoltech.task.Repositories;

import com.skoltech.task.Models.Entities.ObjectEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObjectRepository extends CrudRepository<ObjectEntity, Integer> {
    @Query(value = "SELECT * FROM sk_db.public.object OBJ WHERE OBJ.id = :objectId",nativeQuery = true)
    Optional<ObjectEntity> findByIndex(@Param("objectId") Integer objectId);
}
