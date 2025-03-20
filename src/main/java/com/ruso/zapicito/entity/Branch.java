package com.ruso.zapicito.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@NoArgsConstructor
@Table(name = "branch")
public class Branch extends BaseEntity {

    private String uuid;

    private String name;
    private String slug;
    private boolean isCopyrighted;
    private boolean isCompanyRating;
    private boolean isUserRating;
    private boolean isPublic;
    private boolean isAddressHidden;
    private String timezone;
    private String timezoneShort;
    private String timezoneOffset;
    private Integer orderCount;
    private boolean isAttached;
    private Integer attachedProductsCount;
    private String branchCategory;
    private String image;

    @Embedded
    private BranchProfile profile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private BranchAddress address;

    @ElementCollection
    @CollectionTable(name = "branch_amenity", joinColumns = @JoinColumn(name = "branch_id"))
    @Column(name = "amenity")
    private List<String> amenities;

    @ElementCollection
    @CollectionTable(name = "branch_image", joinColumns = @JoinColumn(name = "branch_id"))
    @Column(name = "image")
    private List<String> images;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    private Company company;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private List<FlatSchedule> flatSchedule;

    @ManyToMany(mappedBy = "branches")
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "branch_service",
            joinColumns = @JoinColumn(name = "branch_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    @JsonBackReference
    private List<Service> services = new ArrayList<>();

    @Data
    @Embeddable
    public static class BranchProfile {
        private String companyType;
        private String currency;
        private Boolean isPhoneHidden;
        private String locale;
        private String language;
        private String description;
        private String instagram;
        private String facebook;
        private String whatsapp;
        private String viber;
        private String telegram;
        private String tiktok;
        private String youtube;
        private String vk;

        @ElementCollection
        @CollectionTable(name = "branch_profile_phones", joinColumns = @JoinColumn(name = "profile_id"))
        private List<String> phone;
    }
}
