package com.ruso.zapicito.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    private String name;
    private String slug;
    private String countryCode;
    private Profile profile;
    private List<String> images;
    private Address address;
    private boolean isAddressHidden;

    @Data
    public static class Profile {
        private Integer industryId;
        private String currency;
        private String locale;
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
            private String county;
        }

        @Data
        public static class Position {
            private Double lat;
            private Double lng;
        }
    }

}
