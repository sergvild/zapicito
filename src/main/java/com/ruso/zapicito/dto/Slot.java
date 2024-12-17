package com.ruso.zapicito.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Slot {
    private LocalDateTime dateTime;
    private Long length;
    private Long sumLength;
    private String time;
}
