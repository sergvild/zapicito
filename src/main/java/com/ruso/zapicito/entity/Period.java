package com.ruso.zapicito.entity;

import com.ruso.zapicito.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "period")
public class Period extends BaseEntity {
    private String date;
    private String start;
    private String end;
}
