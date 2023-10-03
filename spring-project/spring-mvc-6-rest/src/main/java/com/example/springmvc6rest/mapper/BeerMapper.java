package com.example.springmvc6rest.mapper;

import com.example.springmvc6rest.domain.Beer;
import com.example.springmvc6rest.dto.BeerDto;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDto dto);

    BeerDto beerToBeerDto(Beer beer);

}