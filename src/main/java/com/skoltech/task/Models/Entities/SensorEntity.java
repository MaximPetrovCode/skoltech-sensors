package com.skoltech.task.Models.Entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Constraint;

@Data
@Entity
@Table(name = "Sensor")
public class SensorEntity {
    @Id
    @Column(unique = true)
    private Integer id;
}
