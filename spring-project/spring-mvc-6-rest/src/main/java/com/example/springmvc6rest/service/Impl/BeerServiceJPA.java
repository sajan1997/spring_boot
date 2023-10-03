package com.example.springmvc6rest.service.Impl;

import com.example.springmvc6rest.dto.BeerDto;
import com.example.springmvc6rest.mapper.BeerMapper;
import com.example.springmvc6rest.repository.BeerRepository;
import com.example.springmvc6rest.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDto> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .toList();
    }

    @Override
    public Optional<BeerDto> getBeerById(UUID id) {
        return Optional
                .ofNullable(beerMapper.beerToBeerDto(
                beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beer) {
        return null;
    }

    @Override
    public void updateBeerById(UUID beerId, BeerDto beer) {

    }

    @Override
    public void deleteById(UUID beerId) {

    }

    @Override
    public void patchBeerById(UUID beerId, BeerDto beer) {

    }

}
