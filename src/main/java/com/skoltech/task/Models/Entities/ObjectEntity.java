package com.skoltech.task.Models.Entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Object")
public class ObjectEntity {
    @Id
    private Integer id;
}
