package com.ruso.zapicito.service;

import com.ruso.zapicito.dto.SlotDto;
import org.springframework.stereotype.Service;

@Service
public class BookingService {


    public BookingService() {
    }

    public SlotDto findAvailableSlots(){
        return new SlotDto();
    }

}
