package com.ruso.zapicito.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ruso.zapicito.dto.ServiceCategoryDto;
import com.ruso.zapicito.dto.ServiceDto;
import com.ruso.zapicito.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "service_category")
public class ServiceCategory extends BaseEntity {
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy="category")
    private Set<Service> services;

    public ServiceCategory(String name) {
        super();
        this.name = name;
    }

    public ServiceCategory(ServiceCategoryDto serviceCategoryDto) {
        super();
        this.name = serviceCategoryDto.getName();
    }
}
