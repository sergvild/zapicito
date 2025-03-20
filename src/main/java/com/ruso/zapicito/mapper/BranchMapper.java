package com.ruso.zapicito.mapper;

import com.ruso.zapicito.dto.BranchDto;
import com.ruso.zapicito.entity.Branch;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class BranchMapper {

    private final ModelMapper modelMapper;

    public BranchMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BranchDto toDto(Branch branch) {
        return modelMapper.map(branch, BranchDto.class);
    }

    public Branch fromDto(BranchDto branchDto) {
        return modelMapper.map(branchDto, Branch.class);
    }

}