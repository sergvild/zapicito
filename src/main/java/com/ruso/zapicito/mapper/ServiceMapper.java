package com.ruso.zapicito.mapper;

import com.ruso.zapicito.dto.ServiceDto;
import com.ruso.zapicito.entity.Service;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class ServiceMapper {

    private final ModelMapper modelMapper;

    public ServiceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ServiceDto toDto(Service service) {
        return modelMapper.map(service, ServiceDto.class);
    }

    public Service fromDto(ServiceDto serviceDto) {
        return modelMapper.map(serviceDto, Service.class);
    }

}
