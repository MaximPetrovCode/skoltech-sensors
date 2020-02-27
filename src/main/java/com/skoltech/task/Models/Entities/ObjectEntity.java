package com.skoltech.task.Models.Entities;

import com.skoltech.task.Models.Entities.Helpers.ObjectToSensor;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "Object")
public class ObjectEntity {
    @Id
    @Column(unique = true)
    private Integer id;

    @OneToMany(mappedBy = "sensor")
    private Set<ObjectToSensor> link;
}
