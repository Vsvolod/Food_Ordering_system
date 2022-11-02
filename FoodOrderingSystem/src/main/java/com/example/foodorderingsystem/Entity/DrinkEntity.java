package com.example.foodorderingsystem.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drinks")
@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class DrinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private double price;

    public DrinkEntity(Long id, String name, Double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
