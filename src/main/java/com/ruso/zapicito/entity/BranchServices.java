package com.ruso.zapicito.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ruso.zapicito.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "branch_services")
@EqualsAndHashCode
public class BranchServices extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "branch_id")
    @JsonManagedReference
    Branch branch;

    @ManyToOne
    @JoinColumn(name = "service_id")
    @JsonManagedReference
    Service service;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "branch_employee_service",
            joinColumns = { @JoinColumn(name = "branch_service_id") },
            inverseJoinColumns = { @JoinColumn(name = "employee_id") })
    private Set<Employee> employees = new HashSet<>();

    public BranchServices(Branch branch, Service service) {
        super();
        this.branch = branch;
        this.service = service;
    }
}
