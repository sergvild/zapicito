package com.ruso.zapicito.mapper;

import com.ruso.zapicito.dto.AppointmentDto;
import com.ruso.zapicito.dto.ServiceDto;
import com.ruso.zapicito.entity.Appointment;
import com.ruso.zapicito.entity.Service;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class AppointmentMapper {

    private final ModelMapper modelMapper;

    public AppointmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AppointmentDto toDto(Appointment appointment) {
        modelMapper.typeMap(Service.class, ServiceDto.class).addMappings(mapper ->
                mapper.skip(src -> src.getCategory().getId(), ServiceDto::setCategory)
        );
        return modelMapper.map(appointment, AppointmentDto.class);
    }

    public Appointment fromDto(AppointmentDto appointmentResponseDTO) {
        return modelMapper.map(appointmentResponseDTO, Appointment.class);
    }

}
