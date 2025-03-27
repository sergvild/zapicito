package com.ruso.zapicito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryServiceDto {
    private CategoryDto category;
    private List<ServiceDto> services;

}
