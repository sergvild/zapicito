package com.ruso.zapicito.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ruso.zapicito.entity.ServiceCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {

    @NotNull
    @NotEmpty
    private String name;
    
    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @NotEmpty
    private String category;

    @NotNull
    @NotEmpty
    private String price;

    @NotNull
    @NotEmpty
    private String duration;
}
