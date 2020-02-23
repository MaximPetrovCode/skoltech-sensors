package com.skoltech.task.Models.Entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Access(value = AccessType.FIELD)
@Table(name = "Value")
public class ValueEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "created_at")
    private Long time;

    @Column(name = "value")
    private Double value;

    @ManyToOne(targetEntity = ObjectEntity.class)
    private ObjectEntity object;

    @ManyToOne(targetEntity = SensorEntity.class)
    private SensorEntity sensor;
}
