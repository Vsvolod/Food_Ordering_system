package com.example.foodorderingsystem.Controller;

import com.example.foodorderingsystem.Entity.DrinkEntity;
import com.example.foodorderingsystem.Entity.MainDishEntity;
import com.example.foodorderingsystem.repository.DrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drink")
public class DrinkController {
    private final DrinkRepository drinkRepository;

    @GetMapping("/all_drinks")
    public List<DrinkEntity> getAllDrinks(){
        return drinkRepository.findAll();
    }

    @GetMapping("/get_current_drink/{current_drink}")
    public List<DrinkEntity> getCurrentDrink(@PathVariable(value = "current_drink") String name){
        return drinkRepository.findByName(name);
    }

    @PostMapping("/new_drink")
    public DrinkEntity addDrink(@RequestBody DrinkEntity drinkEntity){
        return drinkRepository.save(drinkEntity);
    }

}
