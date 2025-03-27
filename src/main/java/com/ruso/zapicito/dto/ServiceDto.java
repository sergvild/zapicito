package com.ruso.zapicito.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {

    private String name;
    private BigDecimal price;
    private BigDecimal basePrice;
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
    private BigDecimal priceDiscount;
    private Integer expenses;

    private Long category;
    private String priceType;
    private Long taxId;
    private String color;
    private boolean isAttached;
    private boolean isCollective;
    private Integer usersRequired;
    private Integer usersMax;
}
