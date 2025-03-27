package com.ruso.zapicito.mapper;

import com.ruso.zapicito.dto.ServiceDto;
import com.ruso.zapicito.entity.Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;


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

    public List<ServiceDto> toDtoList(List<Service> serviceList) {
        modelMapper.typeMap(Service.class, ServiceDto.class).addMappings(mapper ->
                mapper.skip(src -> src.getCategory().getId(), ServiceDto::setCategory)
        );
        TypeToken<List<ServiceDto>> typeToken = new TypeToken<>() {};
        return modelMapper.map(serviceList, typeToken.getType());
    }

}
