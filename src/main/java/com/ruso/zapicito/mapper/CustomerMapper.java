package com.ruso.zapicito.mapper;

import com.ruso.zapicito.dto.CustomerDto;
import com.ruso.zapicito.entity.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class CustomerMapper {

    private final ModelMapper modelMapper;

    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerDto toDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    public Customer fromDto(CustomerDto customerDto) {
        return modelMapper.map(customerDto, Customer.class);
    }

    public void updateFromDto(CustomerDto customerDto, Customer customer) {
        modelMapper.map(customerDto, customer);
    }
}
