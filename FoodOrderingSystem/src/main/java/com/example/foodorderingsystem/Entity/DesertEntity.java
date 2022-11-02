package com.example.foodorderingsystem.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "deserts")
@Setter @Getter
//@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class DesertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private double price;

    public DesertEntity(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
