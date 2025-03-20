package com.ruso.zapicito.entity;

import com.ruso.zapicito.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company")
public class Company extends BaseEntity {
    private String uuid;
    private String name;
    private String slug;
    private String countryCode;
    private Boolean isAddressHidden;

    @Embedded
    private Profile profile;

    @Embedded
    private Address address;

    @ElementCollection
    @CollectionTable(name = "company_images", joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "image")
    private List<String> images;

    @Embeddable
    @Data
    public static class Profile {
        private Integer industryId;
        private String currency;
        private String locale;
    }

    @Embeddable
    @Data
    public static class Address {
        private String description;
        private String countryIso;
        private String region;
        private String city;
        private String postalCode;
        private String address;
        private String apt;
        private String timezone;

        @Embedded
        private Meta meta;

        @Embedded
        private Position position;

        @Embeddable
        @Data
        public static class Meta {
            private String googleCountryName;
            private String main;
            private String secondary;
            private String county;
        }

        @Embeddable
        @Data
        public static class Position {
            private Double lat;
            private Double lng;
        }
    }
}
