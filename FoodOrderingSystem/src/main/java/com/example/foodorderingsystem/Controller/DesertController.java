package com.example.foodorderingsystem.Controller;

import com.example.foodorderingsystem.Entity.DesertEntity;
import com.example.foodorderingsystem.Entity.MainDishEntity;
import com.example.foodorderingsystem.repository.DesertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/desert")
public class DesertController {
    private final DesertRepository desertRepository;

    @GetMapping("/all_deserts")
    public List<DesertEntity> getAllDesserts(){
        return desertRepository.findAll();
    }

    @GetMapping("/get_current_dessert/{current_desert}")
    public List<DesertEntity> getCurrentDessert(@PathVariable(value = "current_desert", required = true) String name){
        return desertRepository.findByName(name);
    }

    @PostMapping("/new_desert")
    private DesertEntity addDessert(@RequestBody DesertEntity desertEntity){
        return desertRepository.save(desertEntity);
    }


}
