package com.ruso.zapicito.mapper;

import com.ruso.zapicito.dto.EmployeeDto;
import com.ruso.zapicito.dto.UserDto;
import com.ruso.zapicito.entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class EmployeeMapper {

    private final ModelMapper modelMapper;

    public EmployeeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EmployeeDto toDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    public Employee fromDto(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }

    public Employee fromDto(UserDto userDto) {
        return modelMapper.map(userDto, Employee.class);
    }

    public void updateFromDto(EmployeeDto employeeDto, Employee employee) {
        modelMapper.map(employeeDto, employee);
    }

}
