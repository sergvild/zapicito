package com.ruso.zapicito.entity;

import com.ruso.zapicito.entity.base.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "branch_address")
public class BranchAddress extends BaseEntity {
    private String countryIso;
    private String region;
    private String city;
    private String postalCode;
    private String address1;
    private String apt;
    private String timezone;
    private String timezoneShort;
    private String timezoneOffset;

    @Embedded
    private Position position;

    @Embedded
    private Meta meta;

    @Data
    @Embeddable
    public static class Position {
        private Double lat;
        private Double lng;
    }

    @Data
    @Embeddable
    public static class Meta{
        private String googleCountryName;
    }
}
