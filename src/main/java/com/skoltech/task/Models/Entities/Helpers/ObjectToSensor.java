package com.skoltech.task.Models.Entities.Helpers;

import com.skoltech.task.Models.Entities.ObjectEntity;
import com.skoltech.task.Models.Entities.SensorEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "objects_to_sensors")
public class ObjectToSensor {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "object_id")
    private ObjectEntity object;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private SensorEntity sensor;
}
