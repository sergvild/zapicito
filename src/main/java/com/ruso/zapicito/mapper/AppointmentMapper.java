package com.ruso.zapicito.mapper;

import com.ruso.zapicito.dto.AppointmentDto;
import com.ruso.zapicito.entity.Appointment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class AppointmentMapper {

    private final ModelMapper modelMapper;

    public AppointmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AppointmentDto toDto(Appointment appointment) {
        return modelMapper.map(appointment, AppointmentDto.class);
    }

    public Appointment fromDto(AppointmentDto appointmentResponseDTO) {
        return modelMapper.map(appointmentResponseDTO, Appointment.class);
    }

}
