package com.skoltech.task.Services;

import com.skoltech.task.Models.Dtos.Requests.DataRequest;
import com.skoltech.task.Models.Entities.Helpers.AvgValue;
import com.skoltech.task.Models.Entities.Helpers.ObjectToSensor;
import com.skoltech.task.Models.Entities.ObjectEntity;
import com.skoltech.task.Models.Entities.SensorEntity;
import com.skoltech.task.Models.Entities.ValueEntity;
import com.skoltech.task.Repositories.ConnectRepository;
import com.skoltech.task.Repositories.ObjectRepository;
import com.skoltech.task.Repositories.SensorRepository;
import com.skoltech.task.Repositories.ValueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DataService {
    private final ValueRepository valueRepository;

    private final ObjectRepository objectRepository;

    private final SensorRepository sensorRepository;

    private final ConnectRepository connectRepository;

    public DataService(ValueRepository valueRepository, ObjectRepository objectRepository, SensorRepository sensorRepository, ConnectRepository connectRepository) {
        this.valueRepository = valueRepository;
        this.objectRepository = objectRepository;
        this.sensorRepository = sensorRepository;
        this.connectRepository = connectRepository;
    }

    public Integer saveValues(List<DataRequest> values) {
        values.forEach(v -> {
            ValueEntity entity = new ValueEntity();

            Optional<SensorEntity> sensor = sensorRepository.findByIndex(v.getSensorId());
            SensorEntity sensorEntity = sensor.orElseGet(() -> {
                SensorEntity sen = new SensorEntity();
                sen.setId(v.getSensorId());
                return sensorRepository.save(sen);
            });

            Optional<ObjectEntity> object = objectRepository.findByIndex(v.getObjectId());
            ObjectEntity objectEntity = object.orElseGet(() -> {
                ObjectEntity obj = new ObjectEntity();
                obj.setId(v.getObjectId());
                return objectRepository.save(obj);
            });

            entity.setSensor(sensorEntity);
            entity.setObject(objectEntity);
            entity.setTime(v.getTime());
            entity.setValue(v.getValue());

            Optional<ObjectToSensor> connect = connectRepository.findByObjectAndSensor(entity.getObject().getId(), entity.getSensor().getId());
            ObjectToSensor connectEntity = connect.orElseGet(() -> {
                ObjectToSensor con = new ObjectToSensor();
                con.setObject(objectEntity);
                con.setSensor(sensorEntity);
                return this.connectRepository.save(con);
            });

            valueRepository.save(entity);
        });

        this.deleteDuplicates();

        Optional<Integer> amount = valueRepository.countAll();
        return amount.orElse(0);
    }

    private void deleteDuplicates() {
        valueRepository.deleteDuplicates();
    }

    public Optional<List<ValueEntity>> getLatestById(Integer id) {
        return valueRepository.findLatest(id);
    }

    public Optional<List<AvgValue>> getAvg() {
        return Optional.ofNullable(valueRepository.getAvg());
    }

    public Optional<List<ValueEntity>> getByInterval(Integer sensorId, Long from, Long to) {
        return valueRepository.findByInterval(sensorId, from, to);
    }
}
