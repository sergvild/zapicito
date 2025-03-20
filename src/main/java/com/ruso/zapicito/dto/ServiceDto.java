package com.ruso.zapicito.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {

    private String name;
    private Integer price;
    private Integer basePrice;
    private String duration;
    private String durationWithoutPause;
    private String pause;
    private boolean isDurationHidden;
    private boolean isPriceHidden;
    private String description;
    private boolean live;
    private boolean liveInWidget;
    private String serviceCard;
    private List<String> goods;
    private boolean startingAt;
    private boolean isHotPrice;
    private boolean isTop;
    private boolean isAutoAllocated;
    private Integer priceDiscount;
    private Integer expenses;

    private List<Long> categories;
    private String priceType;
    private Long taxId;
    private String color;
    private boolean isAttached;
    private boolean isCollective;
    private Integer usersRequired;
    private Integer usersMax;
}
