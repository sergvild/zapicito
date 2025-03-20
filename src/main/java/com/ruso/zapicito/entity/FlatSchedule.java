package com.ruso.zapicito.entity;

import com.ruso.zapicito.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "flat_schedule")
public class FlatSchedule extends BaseEntity {
    private String type;
    private String start;
    private String end;
}
