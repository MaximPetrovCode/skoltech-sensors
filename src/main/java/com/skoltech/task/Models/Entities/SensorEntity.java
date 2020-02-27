package com.skoltech.task.Models.Entities;

import com.skoltech.task.Models.Entities.Helpers.ObjectToSensor;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "Sensor")
public class SensorEntity {
    @Id
    @Column(unique = true)
    private Integer id;

    @OneToMany(mappedBy ="object")
    private Set<ObjectToSensor> link;
}
