package com.skoltech.task.Services;

import com.skoltech.task.Models.Dtos.Requests.DataRequest;
import com.skoltech.task.Models.Entities.Helpers.AvgValue;
import com.skoltech.task.Models.Entities.ObjectEntity;
import com.skoltech.task.Models.Entities.SensorEntity;
import com.skoltech.task.Models.Entities.ValueEntity;
import com.skoltech.task.Repositories.ObjectRepository;
import com.skoltech.task.Repositories.SensorRepository;
import com.skoltech.task.Repositories.ValueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataService {
    private final ValueRepository valueRepository;

    private final ObjectRepository objectRepository;

    private final SensorRepository sensorRepository;

    public DataService(ValueRepository valueRepository, ObjectRepository objectRepository, SensorRepository sensorRepository) {
        this.valueRepository = valueRepository;
        this.objectRepository = objectRepository;
        this.sensorRepository = sensorRepository;
    }

    public Integer saveValues(List<DataRequest> values) {
        List<ValueEntity> entities = values.stream().map(v -> {
            ValueEntity entity = new ValueEntity();

            SensorEntity sensorEntity = new SensorEntity();
            sensorEntity.setId(v.getSensorId());
            SensorEntity senEntity = sensorRepository.save(sensorEntity);
            entity.setSensor(senEntity);

            ObjectEntity objectEntity = new ObjectEntity();
            objectEntity.setId(v.getObjectId());
            ObjectEntity objEntity = objectRepository.save(objectEntity);
            entity.setObject(objEntity);

            entity.setTime(v.getTime());
            entity.setValue(v.getValue());
            return entity;
        }).collect(Collectors.toList());
        valueRepository.saveAll(entities);
        this.deleteDuplicates();
        Optional<Integer> amount = valueRepository.countAll();
        return amount.orElse(0);
    }

    private void deleteDuplicates() {
        valueRepository.deleteDuplicates();
    }

    public Optional<ValueEntity> getLatestById(Integer id) {
        return valueRepository.findLatest(id);
    }

    public Optional<List<AvgValue>> getAvg() {
        return Optional.ofNullable(valueRepository.getAvg());
    }

    public Optional<List<ValueEntity>> getByInterval(Integer id, Long from,  Long to) {
        return valueRepository.findByInterval(id, from, to);
    }
}
