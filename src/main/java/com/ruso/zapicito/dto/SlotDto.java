package com.ruso.zapicito.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlotDto {
    private Date date;
    private List<Slot> slots;
}
