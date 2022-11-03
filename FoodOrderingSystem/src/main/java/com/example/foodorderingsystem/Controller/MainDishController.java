package com.example.foodorderingsystem.Controller;


import com.example.foodorderingsystem.Entity.MainDishEntity;
import com.example.foodorderingsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main_dish")
public class MainDishController {

    private final MainDishRepository mainDishRepository;

    @GetMapping("/all_dishes")
    public List<MainDishEntity> getAllDishes(){
        return mainDishRepository.findAll();
    }

    @GetMapping("dish_by_name/{name}")
    @ResponseBody
    public List<MainDishEntity> getCurrentDish(@PathVariable(value = "name") String name){
        return mainDishRepository.findByName(name);
    }

    @GetMapping("/dish_by_Country/{country}")
    public List<MainDishEntity> getDishesByCountry(@PathVariable String country){
        return mainDishRepository.findAllByCountry(country);
    }



    @PostMapping("/new_main_dish")
    public MainDishEntity addDish(@RequestBody MainDishEntity mainDishEntity){
        return mainDishRepository.save(mainDishEntity);
    }


}
