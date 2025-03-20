package com.ruso.zapicito.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BranchDto {
    private String name;
    private boolean isPublic;
    private boolean isAddressHidden;
    private String branchCategory;
    private List<String> amenities;
    private Profile profile;
    private Address address;
    private Schedule schedule;
    private boolean isAttached;

    @Data
    public static class Profile {
        private List<String> phone;
        private boolean isPhoneHidden;
        private String description;
    }

    @Data
    public static class Address {
        private String description;
        private Meta meta;
        private String countryIso;
        private String region;
        private String city;
        private String postalCode;
        private String address1;
        private String apt;
        private Position position;
        private String timezone;

        @Data
        public static class Meta {
            private String googleCountryName;
            private String main;
            private String secondary;
            private String street;
            private String house;
            private String county;
        }

        @Data
        public static class Position {
            private Double lat;
            private Double lng;
        }
    }

    @Data
    public static class Schedule {
        private String timezone;
        private Map<String, Object> days; // Assuming each day's schedule could be null or dynamic
    }
}