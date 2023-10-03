package com.example.springmvc6rest.service;

import com.example.springmvc6rest.dto.BeerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface BeerService {

        List<BeerDto> listBeers();

        Optional<BeerDto> getBeerById(UUID id);

        BeerDto saveNewBeer(BeerDto beer);

        void updateBeerById(UUID beerId, BeerDto beer);

        void deleteById(UUID beerId);

        void patchBeerById(UUID beerId, BeerDto beer);

}

