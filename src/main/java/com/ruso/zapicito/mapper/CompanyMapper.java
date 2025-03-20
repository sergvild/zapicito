package com.ruso.zapicito.mapper;

import com.ruso.zapicito.dto.CompanyDto;
import com.ruso.zapicito.entity.Company;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class CompanyMapper {

    private final ModelMapper modelMapper;

    public CompanyMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CompanyDto toDto(Company company) {
        return modelMapper.map(company, CompanyDto.class);
    }

    public Company fromDto(CompanyDto companyDto) {
        return modelMapper.map(companyDto, Company.class);
    }

}
