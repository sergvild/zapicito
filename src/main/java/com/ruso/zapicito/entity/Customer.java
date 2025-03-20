package com.ruso.zapicito.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruso.zapicito.entity.base.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer extends User {
    private boolean isPublicProfile;
    private boolean hasPublicProfile;

    private Integer visitsTotal;
    private Integer bookingsTotal;
    private Integer cancellationsTotal;
    private Double bookedSumAmount;
    private Double paidSumAmount;
    private Double lostIncome;
    private Double minimumCheck;
    private Double maximumCheck;
    private Double averageCheck;

    private Integer scheduledVisits;
    private Integer noShowCounter;

    private Timestamp nextAppointment;
    private Timestamp firstVisitedAt;
    private Timestamp lastBookedAt;
    private Timestamp lastOnlineAt;
    private Timestamp lastVisitedAt;

    @ElementCollection
    @CollectionTable(name = "tags")
    private List<String> tags;

    @ElementCollection
    @CollectionTable(name = "files")
    private List<String> files;

    @JsonIgnore
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="role_id", nullable=false)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

}
