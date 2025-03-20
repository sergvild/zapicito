package com.ruso.zapicito.mapper;

import com.ruso.zapicito.dto.PeriodDto;
import com.ruso.zapicito.entity.Period;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PeriodMapper {

    private final ModelMapper modelMapper;

    public PeriodMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PeriodDto toDto(Period period) {
        return modelMapper.map(period, PeriodDto.class);
    }

    public Period fromDto(PeriodDto periodDto) {
        return modelMapper.map(periodDto, Period.class);
    }

    public List<Period> fromListDto(List<PeriodDto> periodDtoList) {
        TypeToken<List<Period>> typeToken = new TypeToken<>() {};

        return modelMapper.map(periodDtoList, typeToken.getType());
    }

}
