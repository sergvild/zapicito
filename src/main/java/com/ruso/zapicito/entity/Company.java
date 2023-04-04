package com.ruso.zapicito.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ruso.zapicito.dto.CompanyDto;
import com.ruso.zapicito.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company extends BaseEntity {
    private String name;
    private String description;
    private String address;
    private String phone;

    @JsonBackReference
    @OneToMany(mappedBy="company")
    private Set<Branch> branches;

    public Company(CompanyDto companyDto) {
        super();
        this.name = companyDto.getName();
        this.description = companyDto.getDescription();
        this.address = companyDto.getAddress();
        this.phone = companyDto.getPhone();
    }
}
