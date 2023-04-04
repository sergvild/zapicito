package com.ruso.zapicito.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ruso.zapicito.dto.BranchDto;
import com.ruso.zapicito.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Branch extends BaseEntity {
    private String name;
    private String description;
    private String address;
    private String phone;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    private Company company;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE})
    @JoinTable(name = "branch_employee",
            joinColumns = { @JoinColumn(name = "branch_id") },
            inverseJoinColumns = { @JoinColumn(name = "employee_id") })
    @JsonManagedReference
    private Set<Employee> employees = new HashSet<>();


    @OneToMany(mappedBy = "branch")
    @JsonBackReference
    Set<BranchServices> branchServices = new HashSet<>();

    public Branch(BranchDto branchDto) {
        super();
        this.name = branchDto.getName();
        this.description = branchDto.getDescription();
        this.address = branchDto.getAddress();
        this.phone = branchDto.getPhone();
    }
}
