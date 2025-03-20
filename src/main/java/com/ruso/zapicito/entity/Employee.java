package com.ruso.zapicito.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ruso.zapicito.entity.base.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employee")
public class Employee extends User {

    private Long departmentId;
    private String statusText;
    private String statusSys;
    private Boolean isPublic;
    private Boolean isShownInWidget;
    private Boolean isShownInCalendar;
    private Boolean hidePosition;

    private String type;
    private Boolean notifyOnAllBookings;

    private boolean notifyOnAllBookingsSms;
    private boolean notifyOnAllBookingsEmail;
    private boolean notifyOnAllBookingsPush;

    @ElementCollection
    @CollectionTable(name = "notify_on_all_bookings_branches", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "branch_id")
    private List<Long> notifyOnAllBookingsBranches;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "branch_employee",
            joinColumns = @JoinColumn(name = "branch_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    @JsonBackReference
    private List<Branch> branches = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "employee_service",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    @JsonBackReference
    private List<Service> services = new ArrayList<>();

}
