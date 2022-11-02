package com.example.foodorderingsystem.Controller;

import com.example.foodorderingsystem.Entity.CountryEntity;
import com.example.foodorderingsystem.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/country")
public class CountryController {
    private final CountryRepository countryRepository;

    @GetMapping("/all_countries")
    public List<CountryEntity> getAllCountries(){
        return countryRepository.findAll();
    }

    @PostMapping("/new_country")
    public CountryEntity addCountry(@RequestBody CountryEntity country){
        return countryRepository.save(country);
    }
}
