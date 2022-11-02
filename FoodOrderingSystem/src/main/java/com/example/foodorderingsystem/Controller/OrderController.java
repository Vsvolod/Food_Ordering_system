package com.example.foodorderingsystem.Controller;

import com.example.foodorderingsystem.Entity.MainDishEntity;
import com.example.foodorderingsystem.Entity.OrderEntity;
import com.example.foodorderingsystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderRepository orderRepository;

    @GetMapping("/clients_order/{clients_id}")
    public List<OrderEntity> getAnOrder(@PathVariable(value ="clients_id" ) Long clients_id ){
        return orderRepository.findByClientId(clients_id);
    }

    @PostMapping("/new_order")
    public OrderEntity recordAnOrder(@RequestBody OrderEntity orderEntity) throws FileNotFoundException {

            return orderRepository.save(orderEntity);
        }


    }
