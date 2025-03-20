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
@EqualsAndHashCode(callSuper = true)
@Table(name = "branch_service")
public class BranchServices extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "branch_id")
    @JsonManagedReference
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "service_id")
    @JsonManagedReference
    private Service service;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "branch_employee_service",
            joinColumns = { @JoinColumn(name = "branch_service_id") },
            inverseJoinColumns = { @JoinColumn(name = "employee_id") })
    private Set<Employee> employees = new HashSet<>();

}
