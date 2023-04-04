package com.ruso.zapicito.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ruso.zapicito.dto.ServiceDto;
import com.ruso.zapicito.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Service extends BaseEntity {
    private String name;
    private String description;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="service_category_id", nullable=false)
    private ServiceCategory category;

    private String price;
    private String duration;

    public Service(ServiceDto serviceDto) {
        super();
        this.name = serviceDto.getName();
        this.description = serviceDto.getDescription();
        this.price = serviceDto.getPrice();
        this.duration = serviceDto.getDuration();
    }
}