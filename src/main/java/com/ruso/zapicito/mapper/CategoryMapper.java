package com.ruso.zapicito.mapper;

import com.ruso.zapicito.dto.CategoryDto;
import com.ruso.zapicito.dto.CompanyDto;
import com.ruso.zapicito.entity.Category;
import com.ruso.zapicito.entity.Company;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class CategoryMapper {

    private final ModelMapper modelMapper;

    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoryDto toDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    public Category fromDto(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

}
