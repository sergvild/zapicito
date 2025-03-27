package com.ruso.zapicito.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpotDto {
    @JsonIgnore
    private String date;
    private String start;
    private String end;
    private Integer quantity;
    private BigDecimal price;
}
